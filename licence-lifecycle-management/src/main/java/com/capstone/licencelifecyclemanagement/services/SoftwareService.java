package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    
    public Software addSoftware(Software software)
    {
        // LocalDate currentDate = LocalDate.now();
        // System.out.println(currentDate);
        // System.out.println(currentDate.until(software.getExpiryDate()));
        // software.setRemainingDays((int) currentDate.until(software.getExpiryDate()).get(ChronoUnit.DAYS));
        return softwarerepository.save(software);
    }

    public List<Software> getSoftwares ()
    {
        return softwarerepository.findAll();
    }

    public void assetCheck()
    {
        List<Software> softwareList = getSoftwares();

        for (Software software : softwareList) {
            LocalDate expiryDate = software.getExpiryDate();

            LocalDate currentDate = LocalDate.now();

            int remainingDays = (int) ChronoUnit.DAYS.between(currentDate, expiryDate);

            if(remainingDays <= 30)
                SendEmail(remainingDays );

            else if(remainingDays < 0)
            {
                software.setIsExpired(true);
                softwarerepository.save(software);
            }
        }

    }

    public void SendEmail(int remainingDays)
    {
        String software = softwarerepository.findById(1).get().getName();
        String emailSubject = "Software License Expiry Reminder";
        String emailMessage = "Your software license for " + software +
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
