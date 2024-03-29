package com.capstone.licencelifecyclemanagement.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getNotifications()
    {
        List<Notification> notifications = notificationRepository.findAll();
        Collections.reverse(notifications);
        return notifications;

    }
}
