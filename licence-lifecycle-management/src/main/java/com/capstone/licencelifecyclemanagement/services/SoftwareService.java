package com.capstone.licencelifecyclemanagement.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;
import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.entitys.ProductType;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.DecommisionedItemRepository;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;

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

    @Autowired
    private DecommisionedItemRepository decommisionedItemRepository;

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

    public boolean decommissionSoftware(int id) {
        List<SoftwarePurchase> softwarePurchases = softwarePurchaseRepository.findBySoftwarePurchaseId_Software_Id(id);
        Software software = softwarerepository.findById(id).get();

        for (SoftwarePurchase softwarePurchase : softwarePurchases) {
            DecommissionedItem decommissionedItem = new DecommissionedItem();
            decommissionedItem.setPurchaseDate(softwarePurchase.getPurchaseDate());
            decommissionedItem.setExpiryDate(softwarePurchase.getSoftwarePurchaseId().getSoftware().getExpiryDate());
            decommissionedItem.setLicenseNumber(softwarePurchase.getSoftwarePurchaseId().getLicenseNumber());
            decommissionedItem.setProductName(software.getName());
            decommissionedItem.setProductType(ProductType.SOFTWARE);

            decommisionedItemRepository.save(decommissionedItem);

            softwarePurchaseRepository.delete(softwarePurchase);
        }

        softwarerepository.deleteById(id);
        return true;
    }

    public List<Software> getSoftware() {
        return softwarerepository.findAll();
    }

    public boolean setExistingCompany(Software software) {
        Optional<SoftwareCompany> existingCompany = softwareCompanyRepository.findById(software.getCompany().getId());
        if (existingCompany.isPresent()) {
            software.setCompany(existingCompany.get());
            return true;
        } 
        return false;
    }

    public boolean createNewCompany(Software software) {
        SoftwareCompany newCompany = softwareCompanyRepository.save(software.getCompany());
        software.setCompany(newCompany);
        return true;
    }

    public boolean setExistingCompany(Software software, SoftwareCompany company) {
        Optional<SoftwareCompany> existingCompany = softwareCompanyRepository.findById(company.getId());
        if (existingCompany.isPresent()) {
            software.setCompany(existingCompany.get());
            return true;
        } 
        return false;
    }

    private boolean createNewCompany(Software software, SoftwareCompany company) {
        SoftwareCompany newCompany = softwareCompanyRepository.save(company);
        software.setCompany(newCompany);
        return true;
    }

    public List<Software> getSoftwares() {
        return softwarerepository.findAll();
    }

    public List<String> assetCheck() {
        List<Software> softwareList = getSoftwares();
        List<String> softwareNotificationList = new ArrayList<>();
        for (Software software : softwareList) {
            LocalDate expiryDate = software.getExpiryDate();
            int remainingDays = calculateRemainingDays(expiryDate);

            if (remainingDays <= 30 && remainingDays > 0) {

                String message = "Your software license for " + software.getName() +
                        " will expire in " + remainingDays + " days. Please take action.";

                softwareNotificationList.add(message);
                Notification notification = new Notification();
                notification.setMessage(message);
                notification.setExpiryDate(software.getExpiryDate());
                notification.setNumberOfDaysLeft(remainingDays);
                notification.setProductName(software.getName());
                notification.setProductType(ProductType.SOFTWARE);
                notificationRepository.save(notification);

            }
        }
        if (!softwareNotificationList.isEmpty()) {
            sendServiceTerminationEmail();
        }
        return softwareNotificationList;
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

    public List<SoftwareCompany> getCompanies() {
        return softwareCompanyRepository.findAll();
    }

    public boolean sendServiceTerminationEmail() {
        System.out.println("software email method called");
        String senderEmail = "reddyksidharth@gmail.com";
        String senderPassword = "ocqs fssp aleh yise ";
        String smtpHost = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("workertemp7@gmail.com"));
            message.setSubject("softwares close to expire");
            message.setText("your Softwares are about to expire please take action.");

            Transport.send(message);
            System.out.println("software email sent");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
