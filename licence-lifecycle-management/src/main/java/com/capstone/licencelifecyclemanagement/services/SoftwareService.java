package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;

@Service
public class SoftwareService {
    @Autowired
    private SoftwareRepository softwarerepository;

    String toEmail = "admin@prodapt.com";

    public Software addSoftware(Software software) {
        return softwarerepository.save(software);
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
