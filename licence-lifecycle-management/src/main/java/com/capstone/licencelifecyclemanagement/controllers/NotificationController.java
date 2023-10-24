package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.services.NotificationService;

@CrossOrigin()
@RestController
@RequestMapping("/notification")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAll")
    public List<Notification> getNotifications()
    {
        return notificationService.getNotifications();
    }
}
