package com.capstone.licencelifecyclemanagement.controllertests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class SoftwareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SoftwareService softwareService;

    @Test
    @WithMockUser
    void testAddSoftware() throws Exception {
        // Arrange
        Software software = new Software();
        software.setId(1);
        software.setName("Microsoft Office");
        software.setLicenseNumber("123456789");
        software.setExpiryDate(LocalDate.now().plusMonths(6));

        when(softwareService.addSoftware(software)).thenReturn(software);

        // Act and Assert
        mockMvc.perform(post("/software/addsoftware")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(software)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetNotExpired() throws Exception {
        // Arrange
        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        when(softwareService.notExpList()).thenReturn(softwareList);

        // Act and Assert
        mockMvc.perform(get("/software/getNotExpired")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    @WithMockUser
    public void testGetAboutExpired() throws Exception {
        // Arrange
        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        when(softwareService.aboutToExpire()).thenReturn(softwareList);

        // Act and Assert
        mockMvc.perform(get("/software/getAboutExpired")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    @WithMockUser
    public void testGetSoftwares() throws Exception {
        // Arrange
        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        when(softwareService.getSoftwares()).thenReturn(softwareList);

        // Act and Assert
        mockMvc.perform(get("/software/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    @WithMockUser
    public void testGetExpiredCount() throws Exception {
    // Arrange
    when(softwareService.expiredSoftwaresCount()).thenReturn(5);

    // Act and Assert
    mockMvc.perform(get("/software/getExpiredCount")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
}

    @Test
    @WithMockUser
    public void testGetNotExpiredCount() throws Exception {
    // Arrange
    when(softwareService.notExpListCount()).thenReturn(5);

    // Act and Assert
    mockMvc.perform(get("/software/getNotExpiredCount")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
}

@Test
@WithMockUser
public void testGetAboutExpiredCount() throws Exception {
    // Arrange
    when(softwareService.aboutToExpireCount()).thenReturn(5);

    // Act and Assert
    mockMvc.perform(get("/software/getAboutExpiredCount")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("5"));
}

    @Test
    @WithMockUser
    public void testRenewSoftware() throws Exception {
        int id = 1;
        SoftwareDto dto = new SoftwareDto();
        dto.setExpiryDate(LocalDate.now().plusMonths(6));

        when(softwareService.renewSoftware(id, dto)).thenReturn("Software renewed successfully");

        mockMvc.perform(post("/software/renew/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))) 
                .andExpect(status().isOk());
    }

@Test
@WithMockUser
public void testPercentageOfSoftwareAboutToExpire() throws Exception {
    // Arrange
    when(softwareService.percentageOfSoftwareAboutToExpire()).thenReturn(40);

    // Act and Assert
    mockMvc.perform(get("/software/percentageAboutToExpire")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("40"));
}

@Test
@WithMockUser
public void testPercentageOfNotExpiredSoftware() throws Exception {
    // Arrange
    when(softwareService.percentageOfNotExpiredSoftware()).thenReturn(40);

    // Act and Assert
    mockMvc.perform(get("/software/percentageNotExpired")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("40"));
}

@Test
@WithMockUser
public void testPercentageOfExpiredSoftware() throws Exception {
    // Arrange
    when(softwareService.percentageOfExpiredSoftware()).thenReturn(40);

    // Act and Assert
    mockMvc.perform(get("/software/percentageExpired")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("40"));
}

    @Test
    @WithMockUser
    public void testDeleteSoftware() throws Exception {
        // Arrange
        int id = 1;

        // Act and Assert
        mockMvc.perform(delete("/software/decomission/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAssetCheck() throws Exception {
        // Arrange
        List<String> assetList = new ArrayList<>();
        assetList.add("Microsoft Office");
        assetList.add("Adobe Photoshop");

        when(softwareService.assetCheck()).thenReturn(assetList);

        // Act and Assert
        mockMvc.perform(post("/software/assetcheck")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(assetList)));
    }

}