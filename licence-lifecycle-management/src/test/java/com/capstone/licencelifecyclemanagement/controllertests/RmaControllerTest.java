package com.capstone.licencelifecyclemanagement.controllertests;

import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.services.RmaService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RmaService rmaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testMoveSoftwareToRma() throws Exception {
        // Arrange
        int softwareId = 1;
        RMA rma = new RMA();
        rma.setReason("Test reason");

        // Act and Assert
        mockMvc.perform(post("/RMA/moveSoftware/{softwareId}", softwareId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rma)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testMoveDeviceToRma() throws Exception {
        // Arrange
        int deviceId = 1;
        RMA rma = new RMA();
        rma.setReason("Test reason");

        // Act and Assert
        mockMvc.perform(post("/RMA/moveDevice/{deviceId}", deviceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rma)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testPutBackSoftware() throws Exception {
        // Arrange
        int id = 1;

        // Act and Assert
        mockMvc.perform(post("/RMA/putBackSoftware/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testPutBackDevice() throws Exception {
        // Arrange
        int id = 1;

        // Act and Assert
        mockMvc.perform(post("/RMA/putBackDevice/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetRma() throws Exception {
        // Act and Assert
        mockMvc.perform(get("/RMA/getRma"))
                .andExpect(status().isOk());
    }


}
