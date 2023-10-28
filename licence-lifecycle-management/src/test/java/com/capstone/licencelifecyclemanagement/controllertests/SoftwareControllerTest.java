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
class SoftwareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SoftwareService softwareService;

    @Test
    @WithMockUser
    void testAddSoftware() throws Exception {

        Software software = new Software();
        software.setId(1);
        software.setName("Microsoft Office");
        software.setLicenseNumber("123456789");
        software.setExpiryDate(LocalDate.now().plusMonths(6));

        when(softwareService.addSoftware(software)).thenReturn(software);

        mockMvc.perform(post("/software/addsoftware")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(software)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetNotExpired() throws Exception {

        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        when(softwareService.notExpList()).thenReturn(softwareList);

        mockMvc.perform(get("/software/getNotExpired")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    @WithMockUser
    void testGetAboutExpired() throws Exception {

        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        when(softwareService.aboutToExpire()).thenReturn(softwareList);

        mockMvc.perform(get("/software/getAboutExpired")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    @WithMockUser
    void testGetSoftwares() throws Exception {

        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        when(softwareService.getSoftwares()).thenReturn(softwareList);

        mockMvc.perform(get("/software/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(softwareList)));
    }

    @Test
    @WithMockUser
     void testGetExpiredCount() throws Exception {
    
    when(softwareService.expiredSoftwaresCount()).thenReturn(5);

    mockMvc.perform(get("/software/getExpiredCount")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
}

    @Test
    @WithMockUser
     void testGetNotExpiredCount() throws Exception {
    
    when(softwareService.notExpListCount()).thenReturn(5);

    mockMvc.perform(get("/software/getNotExpiredCount")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
}

@Test
@WithMockUser
 void testGetAboutExpiredCount() throws Exception {
    
    when(softwareService.aboutToExpireCount()).thenReturn(5);

    mockMvc.perform(get("/software/getAboutExpiredCount")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("5"));
}

    @Test
    @WithMockUser
    void testRenewSoftware() throws Exception {
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
 void testPercentageOfSoftwareAboutToExpire() throws Exception {
    
    when(softwareService.percentageOfSoftwareAboutToExpire()).thenReturn(40);

    mockMvc.perform(get("/software/percentageAboutToExpire")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("40"));
}

@Test
@WithMockUser
 void testPercentageOfNotExpiredSoftware() throws Exception {
    
    when(softwareService.percentageOfNotExpiredSoftware()).thenReturn(40);

    mockMvc.perform(get("/software/percentageNotExpired")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("40"));
}

@Test
@WithMockUser
 void testPercentageOfExpiredSoftware() throws Exception {
    
    when(softwareService.percentageOfExpiredSoftware()).thenReturn(40);

    mockMvc.perform(get("/software/percentageExpired")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("40"));
}

    @Test
    @WithMockUser
    void testDeleteSoftware() throws Exception {

        int id = 1;

        mockMvc.perform(delete("/software/decomission/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAssetCheck() throws Exception {

        List<String> assetList = new ArrayList<>();
        assetList.add("Microsoft Office");
        assetList.add("Adobe Photoshop");

        when(softwareService.assetCheck()).thenReturn(assetList);

        mockMvc.perform(post("/software/assetcheck")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(assetList)));
    }

}