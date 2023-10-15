package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Notification;
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
        SoftwarePurchaseId SPID = new SoftwarePurchaseId(software.getLicenseNumber(), softwareNew);
        SoftwarePurchase sPurchase = new SoftwarePurchase(SPID);
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
            software.setIsExpired(false);
            software.setPurchaseDate(LocalDate.now());
            software.setLicenseNumber(String.valueOf(Math.floor(Math.random() * 10000)));
            softwarerepository.save(software);

            SoftwarePurchaseId SPID = new SoftwarePurchaseId(software.getLicenseNumber(), software);
            SoftwarePurchase sPurchase = new SoftwarePurchase(SPID);
            softwarePurchaseRepository.save(sPurchase);

            return "Software renewed: " + softwareId;
        }

        return "Software not found";
    }

    public List<Software> getSoftware() {
        return softwarerepository.findAll();
    }

    private void setExistingCompany(Software software) {
        Optional<SoftwareCompany> existingCompany = softwareCompanyRepository.findById(software.getCompany().getId());
        if (existingCompany.isPresent()) {
            software.setCompany(existingCompany.get());
        } else {
            throw new EntityNotFoundException("Company with ID " + software.getCompany().getId() + " not found.");
        }
    }

    private void createNewCompany(Software software) {
        SoftwareCompany newCompany = softwareCompanyRepository.save(software.getCompany());
        software.setCompany(newCompany);
    }

    private void setExistingCompany(Software software, SoftwareCompany company) {
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

    public void assetCheck() {
        List<Software> softwareList = getSoftwares();

        for (Software software : softwareList) {
            LocalDate expiryDate = software.getExpiryDate();
            int remainingDays = calculateRemainingDays(expiryDate);

            if (remainingDays <= 30 && remainingDays > 0)
                sendNotification(remainingDays, software);

            else if (remainingDays < 0) {
                software.setIsExpired(true);
                softwarerepository.save(software);
            }
        }

    }

    public List<Software> notExpList() {
        return softwarerepository.findByIsExpired(false);
    }

    public int notExpListCount() {
        List<Software> Softwares = notExpList();
        return Softwares.size();
    }

    public List<Software> expiredSoftwares() {
        return softwarerepository.findByIsExpired(true);
    }

    public int expiredSoftwaresCount() {
        List<Software> Softwares = expiredSoftwares();
        return Softwares.size();
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

    public int aboutToExpireCount() {
        List<Software> Softwares = aboutToExpire();
        return Softwares.size();
    }

    public int calculateRemainingDays(LocalDate expiryDate) {
        LocalDate currentDate = LocalDate.now();

        int remainingDays = (int) ChronoUnit.DAYS.between(currentDate, expiryDate);
        return remainingDays;
    }

    public void sendNotification(int remainingDays, Software software) {
        // Software software = softwarerepository.findById(1).orElse(null); // Replace
        // '1' with the actual software ID
        if (software != null) {
            String subject = "Software License Expiry Reminder";
            String meesage = "Your software license for " + software.getName() +
                    " will expire in " + remainingDays + " days. Please take action.";

            // Create a new Notification entity
            Notification notification = new Notification();
            notification.setMessage(meesage);
            notification.setExpiryDate(software.getExpiryDate());
            notification.setNumberOfDaysLeft(remainingDays);
            notification.setSoftware(software);
            notification.setName(software.getName());
            notification.setIsSoftware(true);
            // Save the notification in the database
            notificationRepository.save(notification);

            System.out.println(meesage);

            // You can also include logic to send an email here if needed.
        } else {
            System.out.println("Software not found or expired.");
        }
    }

}
