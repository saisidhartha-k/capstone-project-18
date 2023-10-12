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
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;

import jakarta.transaction.Transactional;

@Service
public class SoftwareService {
    @Autowired
    private SoftwareRepository softwarerepository;

    @Autowired
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    String toEmail = "admin@prodapt.com";

    // @Transactional
    // public Software addSoftware(Software software) {
    //     Software softwareNew = softwarerepository.save(software);
    //     SoftwarePurchase sPurchase = new SoftwarePurchase();
    //     sPurchase.setSoftware(softwareNew);
    //     softwarePurchaseRepository.save(sPurchase);
    //     return softwareNew;
    // }

    public String RenewSoftware(int softwareId, SoftwareDto dto) {
        Optional<Software> existingSoftware = softwarerepository.findById(softwareId);
       // softwarerepository.deleteById(softwareId);
        if (existingSoftware.isPresent()) {
            Software software = existingSoftware.get();
            software.setCost(dto.getCost());
            software.setExpiryDate(dto.getExpiryDate());
            software.setIsExpired(false);
            software.setPurchaseDate(LocalDate.now());
            software.setLicenseNumber(String.valueOf(Math.floor(Math.random() * 10000)));
            softwarerepository.save(software);
            SoftwarePurchaseId SPID = new SoftwarePurchaseId(software.getLicenseNumber(), software);
            SoftwarePurchase sPurchase =  new SoftwarePurchase(SPID);
            softwarePurchaseRepository.save(sPurchase);
            return "renewed software:" + softwareId; 
        }
        return "not found";

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
                SendEmail(remainingDays, software.getName());

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

    public void SendEmail(int remainingDays, String name) {
        String software = softwarerepository.findById(1).get().getName();
        String emailSubject = "Software License Expiry Reminder";
        String emailMessage = "Your software license for " + name +
                " will expire in " + remainingDays + " days. Please take action.";

        System.out.println(emailMessage);

        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(toEmail);
        // message.setSubject(emailSubject);
        // message.setText(emailMessage);
        // System.out.println(message);
        // JavaMailSender.send(message);

    }
}
