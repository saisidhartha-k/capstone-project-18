package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.entitys.ProductType;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SoftwareService {
    @Autowired
    private SoftwareRepository softwarerepository;

    @Autowired
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    @Autowired
    private SoftwareCompanyRepository softwareCompanyRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    String toEmail = "admin@prodapt.com";

    @Transactional
    public Software addSoftware(Software software) {
        if (software.getCompany() != null && softwareCompanyRepository.existsById(software.getCompany().getId())) {
            setExistingCompany(software);
        } else {
            createNewCompany(software);
        }

        Software softwareNew = softwarerepository.save(software);
        SoftwarePurchaseId softwarePurchaseId = new SoftwarePurchaseId(software.getLicenseNumber(), softwareNew);
        SoftwarePurchase sPurchase = new SoftwarePurchase(softwarePurchaseId);
        softwarePurchaseRepository.save(sPurchase);
        return softwareNew;
    }

    public String renewSoftware(int softwareId, SoftwareDto dto) {
        Optional<Software> existingSoftware = softwarerepository.findById(softwareId);
        if (existingSoftware.isPresent()) {
            Software software = existingSoftware.get();

            if (dto.getCompany() != null && softwareCompanyRepository.existsById(dto.getCompany().getId())) {
                setExistingCompany(software, dto.getCompany());
            } else {
                createNewCompany(software, dto.getCompany());
            }

            software.setCost(dto.getCost());
            software.setExpiryDate(dto.getExpiryDate());
            software.setPurchaseDate(LocalDate.now());
            software.setLicenseNumber(String.valueOf(Math.floor(Math.random() * 10000)));
            softwarerepository.save(software);

            SoftwarePurchaseId softwarePurchaseId = new SoftwarePurchaseId(software.getLicenseNumber(), software);
            SoftwarePurchase sPurchase = new SoftwarePurchase(softwarePurchaseId);
            softwarePurchaseRepository.save(sPurchase);

            return "Software renewed: " + softwareId;
        }

        return "Software not found";
    }

    public void decomissionSoftware(int id) {
         softwarePurchaseRepository.deleteBySoftwarePurchaseId_Software_Id(id);

        softwarerepository.deleteById(id);
       // notificationRepository.deleteAllById(id);
    }

    public List<Software> getSoftware() {
        return softwarerepository.findAll();
    }

    public void setExistingCompany(Software software) {
        Optional<SoftwareCompany> existingCompany = softwareCompanyRepository.findById(software.getCompany().getId());
        if (existingCompany.isPresent()) {
            software.setCompany(existingCompany.get());
        } else {
            throw new EntityNotFoundException("Company with ID " + software.getCompany().getId() + " not found.");
        }
    }

    public void createNewCompany(Software software) {
        SoftwareCompany newCompany = softwareCompanyRepository.save(software.getCompany());
        software.setCompany(newCompany);
    }

    public void setExistingCompany(Software software, SoftwareCompany company) {
        Optional<SoftwareCompany> existingCompany = softwareCompanyRepository.findById(company.getId());
        if (existingCompany.isPresent()) {
            software.setCompany(existingCompany.get());
        } else {
            throw new EntityNotFoundException("Company with ID " + company.getId() + " not found.");
        }
    }

    private void createNewCompany(Software software, SoftwareCompany company) {
        SoftwareCompany newCompany = softwareCompanyRepository.save(company);
        software.setCompany(newCompany);
    }

    public List<Software> getSoftwares() {
        return softwarerepository.findAll();
    }

    public List<String> assetCheck() {
        List<Software> softwareList = getSoftwares();
        List<String> notificationList = new ArrayList<>();
        for (Software software : softwareList) {
            LocalDate expiryDate = software.getExpiryDate();
            int remainingDays = calculateRemainingDays(expiryDate);

            if (remainingDays <= 30 && remainingDays > 0) {

                String meesage = "Your software license for " + software.getName() +
                        " will expire in " + remainingDays + " days. Please take action.";

                notificationList.add(meesage);
                Notification notification = new Notification();
                notification.setMessage(meesage);
                notification.setExpiryDate(software.getExpiryDate());
                notification.setNumberOfDaysLeft(remainingDays);
                notification.setProductName(software.getName());
                notification.setProductType(ProductType.SOFTWARE);
                notificationRepository.save(notification);

                System.out.println(meesage);

            }
        }
        return notificationList;
    }

    public List<Software> notExpList() {
        LocalDate today = LocalDate.now();
        return softwarerepository.findNonExpiredSoftware(today);
    }

    public List<Software> expiredSoftwares() {
        LocalDate today = LocalDate.now(); // Get the current date
        return softwarerepository.findExpiredSoftware(today);
    }

    public int aboutToExpireCount() {
        List<Software> softwares = aboutToExpire();
        return softwares.size();
    }

    public int notExpListCount() {
        List<Software> softwares = notExpList();
        return softwares.size();
    }

    public int expiredSoftwaresCount() {
        List<Software> softwares = expiredSoftwares();
        return softwares.size();
    }

    public List<Software> aboutToExpire() {
        List<Software> aboutToExpireSoftwares = new ArrayList<>();
        List<Software> softwareList = getSoftwares();
        for (Software software : softwareList) {
            LocalDate expiryDate = software.getExpiryDate();
            int remainingDays = calculateRemainingDays(expiryDate);
            if (remainingDays <= 30 && remainingDays > 0) {
                aboutToExpireSoftwares.add(software);
            }
        }
        return aboutToExpireSoftwares;
    }

    public int calculateRemainingDays(LocalDate expiryDate) {
        LocalDate currentDate = LocalDate.now();

        return (int) ChronoUnit.DAYS.between(currentDate, expiryDate);
    }

    public int percentageOfSoftwareAboutToExpire() {
        List<Software> softwareList = aboutToExpire();
        int totalSoftware = getTotalSoftwareCount();
        if (totalSoftware == 0) {
            return 0;
        }

        return (softwareList.size() * 100) / totalSoftware;
    }

    public int percentageOfNotExpiredSoftware() {
        int notExpiredSoftware = notExpListCount();
        int totalSoftware = getTotalSoftwareCount();

        if (totalSoftware == 0) {
            return 0;
        }

        return (notExpiredSoftware * 100) / totalSoftware;
    }

    public int percentageOfExpiredSoftware() {
        int expiredSoftware = expiredSoftwaresCount();
        int totalSoftware = getTotalSoftwareCount();

        if (totalSoftware == 0) {
            return 0;
        }

        return (expiredSoftware * 100) / totalSoftware;
    }

    public int getTotalSoftwareCount() {
        List<Software> allSoftware = getSoftware();

        return allSoftware.size();
    }

    // change this later

    public List<SoftwareCompany> getCompanies() {
        return softwareCompanyRepository.findAll();
    }

}
