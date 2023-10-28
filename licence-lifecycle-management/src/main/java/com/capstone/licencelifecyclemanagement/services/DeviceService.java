package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchaseId;
import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.entitys.ProductType;
import com.capstone.licencelifecyclemanagement.repository.DecommisionedItemRepository;
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

    @Autowired
    private DecommisionedItemRepository decommisionedItemRepository;

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
            }
        }

        if (!deviceNotificationList.isEmpty())
            sendServiceTerminationEmail();

        return deviceNotificationList;
    }

    public int calculateRemainingDays(LocalDate expiryDate) {
        LocalDate currentDate = LocalDate.now();

        return (int) ChronoUnit.DAYS.between(currentDate, expiryDate);
    }

    public boolean setExistingCompany(Device device) {
        Optional<DeviceCompany> existingCompany = deviceCompanyRepository.findById(device.getCompany().getId());
        if (existingCompany.isPresent()) {
            device.setCompany(existingCompany.get());
            return true;
        }
        return false;
    }

    public boolean createNewCompany(Device device) {
        DeviceCompany newCompany = deviceCompanyRepository.save(device.getCompany());
        device.setCompany(newCompany);
        return true;
    }

    public boolean setExistingCompany(Device device, DeviceCompany company) {
        Optional<DeviceCompany> existingCompany = deviceCompanyRepository.findById(company.getId());
        if (existingCompany.isPresent()) {
            device.setCompany(existingCompany.get());
            return true;
        }
        return false;
    }

    public boolean createNewCompany(Device device, DeviceCompany company) {
        DeviceCompany newCompany = deviceCompanyRepository.save(company);
        device.setCompany(newCompany);
        return true;
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

    public boolean decomissionDevice(int id) {
        List<DevicePurchase> devicePurchases = devicePurchaseRepository.findByDevicePurchaseId_Device_Id(id);
        Device device = deviceRepository.findById(id).orElse(null);

        for (DevicePurchase devicePurchase : devicePurchases) {
            DecommissionedItem decommissionedItem = new DecommissionedItem();
            decommissionedItem.setPurchaseDate(devicePurchase.getPurchaseDate());
            decommissionedItem.setExpiryDate(devicePurchase.getDevicePurchaseId().getDevice().getExpiryDate());
            decommissionedItem.setLicenseNumber(devicePurchase.getDevicePurchaseId().getLicenseNumber());
            decommissionedItem.setProductName(device.getName());
            decommissionedItem.setProductType(ProductType.DEVICE);

            decommisionedItemRepository.save(decommissionedItem);

            devicePurchaseRepository.delete(devicePurchase);
        }

        deviceRepository.deleteById(id);
        return true;
    }

    public boolean sendServiceTerminationEmail() {
        System.out.println("device mail method called");
        String senderEmail = "reddyksidharth@gmail.com";
        String senderPassword = "mjuh ufms krvf opvd";
        String smtpHost = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("workertemp7@gmail.com"));
            message.setSubject("devices close to expire");
            message.setText("your devices are about to expire please take action.");

            Transport.send(message);
            System.out.println("device email sent");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
