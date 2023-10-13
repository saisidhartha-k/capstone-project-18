package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DevicePurchaseRepository devicePurchaseRepository;

    String toEmail = "admin@prodapt.com";

    public Device addDevice(Device device) {
        Device newDevice = deviceRepository.save(device);
        DevicePurchaseId devicePurchaseId = new DevicePurchaseId(device.getLicenseNumber(),newDevice);
        DevicePurchase devicePurchase = new DevicePurchase(devicePurchaseId);
        devicePurchaseRepository.save(devicePurchase);
        return newDevice;
    }

    public String renewDevice(int deviceId, DeviceDto dto) {
        Optional<Device> existingDevice = deviceRepository.findById(deviceId);
        
        if (existingDevice.isPresent()) {
            Device device = existingDevice.get();
            device.setCost(dto.getCost());
            device.setExpiryDate(dto.getExpiryDate());
            device.setIsExpired(false);
            device.setLocation(dto.getLocation());
            device.setPurchaseDate(LocalDate.now());
            device.setLicenseNumber(String.valueOf(Math.floor(Math.random() * 10000)));
            deviceRepository.save(device);
            
            DevicePurchaseId devicePurchaseId = new DevicePurchaseId(device.getLicenseNumber(),device);
            DevicePurchase devicePurchase = new DevicePurchase(devicePurchaseId);
            devicePurchaseRepository.save(devicePurchase);
            
            return "Device renewed: " + deviceId;
        }
        return "Device not found";
    }

    public List<Device> getDevices ()
    {
        return deviceRepository.findAll();
    }

}
