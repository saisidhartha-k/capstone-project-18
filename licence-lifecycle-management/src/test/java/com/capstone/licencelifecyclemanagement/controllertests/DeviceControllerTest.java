package com.capstone.licencelifecyclemanagement.controllertests;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.services.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService deviceService;

    @Test
    @WithMockUser
    void testAddDevice() throws Exception {
        // 
        Device device = new Device();
        device.setId(1);
        device.setName("virtual storage");
        device.setLicenseNumber("123456789");
        device.setExpiryDate(LocalDate.now().plusMonths(6));

        when(deviceService.addDevice(device)).thenReturn(device);

        mockMvc.perform(post("/device/adddevice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void testGetDevices() throws Exception {
        // 
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        device.setId(1);
        device.setName("virtual storage");
        device.setLicenseNumber("123456789");
        device.setExpiryDate(LocalDate.now().plusMonths(6));
        devices.add(device);

        when(deviceService.getDevices()).thenReturn(devices);

        
        mockMvc.perform(get("/device/get"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testRenewDevice() throws Exception {
        // 
        int id = 1;
        DeviceDto dto = new DeviceDto();
        dto.setExpiryDate(LocalDate.now().plusMonths(6));

        when(deviceService.renewDevice(id, dto)).thenReturn("Device renewed successfully");

        
        mockMvc.perform(post("/device/renew/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetExpiredDevices() throws Exception {
        // 
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        device.setId(1);
        device.setName("virtual storage");
        device.setLicenseNumber("123456789");
        device.setExpiryDate(LocalDate.now().minusDays(1));
        devices.add(device);

        when(deviceService.expierdDevices()).thenReturn(devices);

        
        mockMvc.perform(get("/device/getExpired"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetAboutExpiredDevices() throws Exception {
        // 
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        device.setId(1);
        device.setName("virtual storage");
        device.setLicenseNumber("123456789");
        device.setExpiryDate(LocalDate.now().plusDays(1));
        devices.add(device);

        when(deviceService.devicesAboutToExpire()).thenReturn(devices);

        
        mockMvc.perform(get("/device/getAboutExpired"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetDevicesAboutToExpireCount() throws Exception {
        // 
        int count = 1;

        when(deviceService.devicesAboutToExpireCount()).thenReturn(count);

        
        mockMvc.perform(get("/device/aboutToExpireCount"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetNotExpiredDeviceCount() throws Exception {
        // 
        int count = 1;

        when(deviceService.devicesNotExpiredCount()).thenReturn(count);

        
        mockMvc.perform(get("/device/notExpiredCount"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetExpiredDevicesCount() throws Exception {
        // 
        int count = 1;

        when(deviceService.expiredDevicesCount()).thenReturn(count);

        
        mockMvc.perform(get("/device/expiredCount"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetPercentageOfDevicesAboutToExpire() throws Exception {
        // 
        int percentage = 10;

        when(deviceService.percentageOfDevicesAboutToExpire()).thenReturn(percentage);

        
        mockMvc.perform(get("/device/percentageAboutToExpire"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetPercentageOfNotExpiredDevices() throws Exception {
        // 
        int percentage = 80;

        when(deviceService.percentageOfNotExpiredDevices()).thenReturn(percentage);

        
        mockMvc.perform(get("/device/percentageNotExpired"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetPercentageOfExpiredDevices() throws Exception {
        // 
        int percentage = 10;

        when(deviceService.percentageOfExpiredDevices()).thenReturn(percentage);

        
        mockMvc.perform(get("/device/percentageExpired"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testDecomissionDevice() throws Exception {
        // 
        int id = 1;

        mockMvc.perform(delete("/device/decomissionDevice/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAssetCheck() throws Exception {
        List<String> assets = new ArrayList<>();
        assets.add("Asset 1");

        when(deviceService.assetCheck()).thenReturn(assets);

        mockMvc.perform(post("/device/assetcheck"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSendmail() throws Exception {
        // 
        boolean result = true;

        when(deviceService.sendServiceTerminationEmail()).thenReturn(result);

        
        mockMvc.perform(post("/device/sendmail"))
                .andExpect(status().isOk());
    }

}
