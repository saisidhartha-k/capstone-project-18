package com.capstone.licencelifecyclemanagement.entitytests;


import com.capstone.licencelifecyclemanagement.entitys.Device;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DeviceTest {

    @Test
     void testDeviceInitialization() {
        Device device = new Device();
        assertNotNull(device);
        assertNull(device.getName());
        assertNull(device.getCompany());
        assertNotNull(device.getLicenseNumber());
        assertEquals(0, device.getNumberOfEmployees());
        assertEquals(0, device.getCost());
        assertNotNull(device.getPurchaseDate());
        assertNull(device.getExpiryDate());
        assertNull(device.getLocation());
    }

}
