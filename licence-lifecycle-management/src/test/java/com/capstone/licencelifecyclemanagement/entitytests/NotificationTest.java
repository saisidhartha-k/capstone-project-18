package com.capstone.licencelifecyclemanagement.entitytests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.licencelifecyclemanagement.entitys.Notification;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        notification.setId(1);
        notification.setProductName("Test Notification");
        notification.setExpiryDate(LocalDate.now());
        notification.setNumberOfDaysLeft(30);
        notification.setMessage("Test Message");
    }

    @Test
    void testGetId() {
        assertEquals(1, notification.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Test Notification", notification.getProductName());
    }

    @Test
    void testGetExpiryDate() {
        assertEquals(LocalDate.now(), notification.getExpiryDate());
    }

    @Test
    void testGetNumberOfDaysLeft() {
        assertEquals(30, notification.getNumberOfDaysLeft());
    }

    @Test
    void testGetMessage() {
        assertEquals("Test Message", notification.getMessage());
    }

}
