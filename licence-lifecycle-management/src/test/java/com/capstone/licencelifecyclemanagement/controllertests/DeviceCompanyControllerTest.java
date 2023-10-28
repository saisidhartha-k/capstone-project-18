package com.capstone.licencelifecyclemanagement.controllertests;

import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.services.DeviceCompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceCompanyService deviceCompanyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testAddCompany() throws Exception {

        DeviceCompany company = new DeviceCompany();
        company.setId(1);
        company.setName("Test Company");

        when(deviceCompanyService.addCompany(company)).thenReturn(company);

        mockMvc.perform(post("/devicecompany/addCompany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAllDeviceCompany() throws Exception {
        mockMvc.perform(get("/devicecompany/deviceCompanies"))
                .andExpect(status().isOk());
    }
}
