package com.capstone.licencelifecyclemanagement.entitytests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.Notification;
import com.capstone.licencelifecyclemanagement.entitys.Software;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationTest {

    private Notification notification;

    @BeforeEach
    public void setUp() {
        notification = new Notification();
        notification.setId(1);
        notification.setSoftware(new Software());
        notification.setDevice(new Device());
        notification.setName("Test Notification");
        notification.setExpiryDate(LocalDate.now());
        notification.setNumberOfDaysLeft(30);
        notification.setMessage("Test Message");
        notification.setIsSoftware(true);
    }

    @Test
    public void testGetId() {
        assertEquals(1, notification.getId());
    }

    // @Test
    // public void testGetSoftware() {
    //     assertEquals(new Software(), notification.getSoftware());
    // }

    // @Test
    // public void testGetDevice() {
    //     assertEquals(new Device(), notification.getDevice());
    // }

    @Test
    public void testGetName() {
        assertEquals("Test Notification", notification.getName());
    }

    @Test
    public void testGetExpiryDate() {
        assertEquals(LocalDate.now(), notification.getExpiryDate());
    }

    @Test
    public void testGetNumberOfDaysLeft() {
        assertEquals(30, notification.getNumberOfDaysLeft());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Test Message", notification.getMessage());
    }



   
}
