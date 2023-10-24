package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchaseId;
import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.entitys.ProductType;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.repository.DeviceCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DevicePurchaseRepository devicePurchaseRepository;

    @Autowired
    private DeviceCompanyRepository deviceCompanyRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    String toEmail = "admin@prodapt.com";

    public Device addDevice(Device device) {
        if (device.getCompany() != null && deviceCompanyRepository.existsById(device.getCompany().getId())) {
            setExistingCompany(device);
        } else {
            createNewCompany(device);
        }

        Device newDevice = deviceRepository.save(device);
        DevicePurchaseId devicePurchaseId = new DevicePurchaseId(device.getLicenseNumber(), newDevice);
        DevicePurchase devicePurchase = new DevicePurchase(devicePurchaseId);
        devicePurchaseRepository.save(devicePurchase);
        return newDevice;
    }

    public String renewDevice(int deviceId, DeviceDto dto) {
        Optional<Device> existingDevice = deviceRepository.findById(deviceId);

        if (existingDevice.isPresent()) {
            Device device = existingDevice.get();

            if (dto.getCompany() != null && deviceCompanyRepository.existsById(dto.getCompany().getId())) {
                setExistingCompany(device, dto.getCompany());
            } else {
                createNewCompany(device, dto.getCompany());
            }

            device.setCost(dto.getCost());
            device.setExpiryDate(dto.getExpiryDate());
            device.setLocation(dto.getLocation());
            device.setPurchaseDate(LocalDate.now());
            device.setLicenseNumber(String.valueOf(Math.floor(Math.random() * 10000)));
            deviceRepository.save(device);

            DevicePurchaseId devicePurchaseId = new DevicePurchaseId(device.getLicenseNumber(), device);
            DevicePurchase devicePurchase = new DevicePurchase(devicePurchaseId);
            devicePurchaseRepository.save(devicePurchase);

            return "Device renewed: " + deviceId;
        }

        return "Device not found";
    }

    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> expierdDevices() {
        LocalDate today = LocalDate.now();
        return deviceRepository.findExpiredDevice(today);
    }

    public List<Device> notExpiredDevice() {
        LocalDate today = LocalDate.now();
        return deviceRepository.findNonExpiredDevice(today);
    }

    public List<Device> devicesAboutToExpire() {
        List<Device> aboutToExpireDevices = new ArrayList<>();
        List<Device> deviceList = getDevices();

        for (Device device : deviceList) {
            LocalDate expiryDate = device.getExpiryDate();
            int remainingDays = calculateRemainingDays(expiryDate);
            if (remainingDays <= 30 && remainingDays > 0) {
                aboutToExpireDevices.add(device);
            }
        }
        return aboutToExpireDevices;
    }

     public List<String> assetCheck() {
        List<Device> deviceList = getDevices();
        List<String> deviceNotificationList = new ArrayList<>();
        for (Device device : deviceList) {
            LocalDate expiryDate = device.getExpiryDate();
            int remainingDays = calculateRemainingDays(expiryDate);

            if (remainingDays <= 30 && remainingDays > 0) {

                String message = "Your device license for " + device.getName() +
                        " will expire in " + remainingDays + " days. Please take action.";

                deviceNotificationList.add(message);
                Notification notification = new Notification();
                notification.setMessage(message);
                notification.setExpiryDate(device.getExpiryDate());
                notification.setNumberOfDaysLeft(remainingDays);
                notification.setProductName(device.getName());
                notification.setProductType(ProductType.DEVICE);
                notificationRepository.save(notification);

                System.out.println(message);

            }
        }
        return deviceNotificationList;
    }

    public int calculateRemainingDays(LocalDate expiryDate) {
        LocalDate currentDate = LocalDate.now();

        return (int) ChronoUnit.DAYS.between(currentDate, expiryDate);
    }

  

    private void setExistingCompany(Device device) {
        Optional<DeviceCompany> existingCompany = deviceCompanyRepository.findById(device.getCompany().getId());
        if (existingCompany.isPresent()) {
            device.setCompany(existingCompany.get());
        } else {
            throw new EntityNotFoundException("Company with ID " + device.getCompany().getId() + " not found.");
        }
    }

    private void createNewCompany(Device device) {
        DeviceCompany newCompany = deviceCompanyRepository.save(device.getCompany());
        device.setCompany(newCompany);
    }

    private void setExistingCompany(Device device, DeviceCompany company) {
        Optional<DeviceCompany> existingCompany = deviceCompanyRepository.findById(company.getId());
        if (existingCompany.isPresent()) {
            device.setCompany(existingCompany.get());
        } else {
            throw new EntityNotFoundException("Company with ID " + company.getId() + " not found.");
        }
    }

    private void createNewCompany(Device device, DeviceCompany company) {
        DeviceCompany newCompany = deviceCompanyRepository.save(company);
        device.setCompany(newCompany);
    }

    public int devicesAboutToExpireCount() {
        List<Device> devices = devicesAboutToExpire();
        return devices.size();
    }

    public int devicesNotExpiredCount() {
        List<Device> devices = notExpiredDevice();
        return devices.size();
    }

    public int expiredDevicesCount() {
        List<Device> devices = expierdDevices();
        return devices.size();
    }

    public int percentageOfDevicesAboutToExpire() {
        List<Device> devices = devicesAboutToExpire();
        int totalDevices = getTotalDeviceCount();

        if (totalDevices == 0) {
            return 0; // Avoid division by zero
        }

        return (devices.size() * 100) / totalDevices;
    }

    public int percentageOfNotExpiredDevices() {
        List<Device> devices = notExpiredDevice();
        int totalDevices = getTotalDeviceCount();

        if (totalDevices == 0) {
            return 0; // Avoid division by zero
        }

        return (devices.size() * 100) / totalDevices;
    }

    public int percentageOfExpiredDevices() {
        List<Device> devices = expierdDevices();
        int totalDevices = getTotalDeviceCount();

        if (totalDevices == 0) {
            return 0;
        }

        return (devices.size() * 100) / totalDevices;
    }

    public int getTotalDeviceCount() {
        List<Device> allDevices = getDevices();

        return allDevices.size();
    }

    public void decomissionDevice(int id) {
        devicePurchaseRepository.deleteByDevicePurchaseId_Device_Id(id);

        deviceRepository.deleteById(id);
    }

}
