package com.capstone.licencelifecyclemanagement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchaseId;
import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.RMARepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;

@Service
public class RmaService {
    @Autowired
    private RMARepository rmaRepository;

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    @Autowired
    private DevicePurchaseRepository devicePurchaseRepository;

    public void moveToRma(int id, RMA rma) {
        Optional<Software> softwareOptional = softwareRepository.findById(id);
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (softwareOptional.isPresent() && rma.getProductType().equals("Software")) {
            Software software = softwareOptional.get();

            // Copy software details into the RMA object
            rma.setCompanyId(software.getCompany().getId());
            rma.setCompanyName(software.getCompany().getName());
            rma.setExpiryDate(software.getExpiryDate());
            rma.setLicenseNumber(software.getLicenseNumber());
            rma.setProductName(software.getName());
            rma.setPurchasDate(software.getPurchaseDate());
            rma.setCost(software.getCost());
            rma.setNumberOfEmployees(software.getNumberOfEmployees());

            rmaRepository.save(rma);
            softwarePurchaseRepository.deleteBySoftwarePurchaseId_Software_Id(id);
            softwareRepository.deleteById(id);
        } else if (deviceOptional.isPresent() && rma.getProductType().equals("Device")) {
            System.out.println("inside");
            Device device = deviceOptional.get();

            // Copy device details into the RMA object
            rma.setCompanyId(device.getCompany().getId());
            rma.setCompanyName(device.getCompany().getName());
            rma.setExpiryDate(device.getExpiryDate());
            rma.setLicenseNumber(device.getLicenseNumber());
            rma.setProductName(device.getName());
            rma.setCost(device.getCost());
            rma.setPurchasDate(device.getPurchaseDate());

            rmaRepository.save(rma);
            devicePurchaseRepository.deleteByDevicePurchaseId_Device_Id(id);
            deviceRepository.deleteById(id);
        }
    }

    public void putBackFromRma(int rmaId) {
        Optional<RMA> rmaOptional = rmaRepository.findById(rmaId);

        if (rmaOptional.isPresent()) {
            RMA rma = rmaOptional.get();

            if ("software".equalsIgnoreCase(rma.getProductType())) {
                // Handle software
                Software software = new Software();
                software.setName(rma.getProductName());
                SoftwareCompany company = new SoftwareCompany();
                company.setId(rma.getCompanyId());
                software.setCompany(company);
                software.setExpiryDate(rma.getExpiryDate());
                software.setLicenseNumber(rma.getLicenseNumber());
                software.setNumberOfEmployees(rma.getNumberOfEmployees());
                software.setCost(rma.getCost());

                // Save to Software repository
                softwareRepository.save(software);
                SoftwarePurchaseId softwarePurchaseId = new SoftwarePurchaseId(software.getLicenseNumber(), software);
                SoftwarePurchase sPurchase = new SoftwarePurchase(softwarePurchaseId);
                softwarePurchaseRepository.save(sPurchase);
            } else if ("device".equalsIgnoreCase(rma.getProductType())) {
                // Handle device
                Device device = new Device();
                device.setName(rma.getProductName());
                DeviceCompany company = new DeviceCompany();
                company.setId(rma.getCompanyId());
                device.setCompany(company);
                device.setExpiryDate(rma.getExpiryDate());
                device.setLicenseNumber(rma.getLicenseNumber());
                device.setCost(rma.getCost());

                deviceRepository.save(device);
                DevicePurchaseId devicePurchaseId = new DevicePurchaseId(device.getLicenseNumber(), device);
                DevicePurchase devicePurchase = new DevicePurchase(devicePurchaseId);
                devicePurchaseRepository.save(devicePurchase);
            }

            rmaRepository.deleteById(rmaId);
        }
    }
}
