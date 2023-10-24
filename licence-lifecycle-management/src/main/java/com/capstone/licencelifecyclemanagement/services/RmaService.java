package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.Available;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.RMARepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;

@Service
public class RmaService {
    @Autowired
    private RMARepository rmaRepository;

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    public void moveSoftwareToRma(int softwareId, String reason) {
        Optional<Software> softwareOpt = softwareRepository.findById(softwareId);
        if (softwareOpt.isPresent()) {
            RMA rma = new RMA();
            Software software = softwareOpt.get();
            software.setAvailable(Available.NOT_AVAILABLE);
            rma.setSoftware(software);
            rma.setRequestDate(LocalDate.now());
            rma.setReason(reason);
            rmaRepository.save(rma);
        }
    }

    public void moveDeviceToRma(int deviceId, String reason) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        if (deviceOpt.isPresent()) {
            RMA rma = new RMA();
            Device device = deviceOpt.get();
            device.setAvailable(Available.NOT_AVAILABLE);
            rma.setDevice(device);
            rma.setRequestDate(LocalDate.now());
            rma.setReason(reason);
            rmaRepository.save(rma);
        }
    }

    private String randomLicense() {
        String salt = "abcdefghijklmno0123456789";
        int randomLength = 10;
        String randomString = "";
        Random rand = new Random();
        for (int i = 0; i < randomLength; i++) {
            randomString += salt.charAt(rand.nextInt(salt.length()));
        }
        return randomString;
    }

    public void putBackSoftwareFromRma(int rmaId) {
        Optional<RMA> rmaOpt = rmaRepository.findById(rmaId);
        if (rmaOpt.isPresent() && rmaOpt.get().getSoftware() != null) {
            RMA rma = rmaOpt.get();
            Software software = rma.getSoftware();

            LocalDate requestDate = rma.getRequestDate();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(requestDate, currentDate);
            int months = period.getYears() * 12 + period.getMonths();

            LocalDate expiryDate = software.getExpiryDate();
            expiryDate = expiryDate.plusMonths(months);
            software.setExpiryDate(expiryDate);

            software.setLicenseNumber(randomLicense());
            software.setAvailable(Available.AVAILABLE);
            rmaRepository.delete(rma);
        }

    }

    public void putBackDeviceFromRma(int rmaId) {
        Optional<RMA> rmaOpt = rmaRepository.findById(rmaId);
    
        if (rmaOpt.isPresent() && rmaOpt.get().getDevice() != null) {
            RMA rma = rmaOpt.get();
            Device device = rma.getDevice();
    
            LocalDate requestDate = rma.getRequestDate();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(requestDate, currentDate);
            int months = period.getYears() * 12 + period.getMonths();
    
            LocalDate expiryDate = device.getExpiryDate();
            expiryDate = expiryDate.plusMonths(months);
            device.setExpiryDate(expiryDate);
    
            device.setLicenseNumber(randomLicense()); // Implement your serial number generation logic
            device.setAvailable(Available.AVAILABLE); // Assuming you have an enumeration for device status
            rmaRepository.delete(rma);
        }
    }

    public List<RMA> getRma()
    {
        return rmaRepository.findAll();
    }
    
}
