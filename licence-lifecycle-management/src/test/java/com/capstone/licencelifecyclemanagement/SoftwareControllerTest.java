package com.capstone.licencelifecyclemanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.capstone.licencelifecyclemanagement.controllers.SoftwareController;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SoftwareControllerTest {

    @InjectMocks
    private SoftwareController softwareController;

    @MockBean
    private SoftwareService softwareService;

    @Test
    public void testGetSoftwares() {
        

        SoftwareCompany mockCompany1 = new SoftwareCompany();
        mockCompany1.setName("Company Name 1");
        mockCompany1.setDescription("Description for Company 1");

        SoftwareCompany mockCompany2 = new SoftwareCompany();
        mockCompany2.setName("Company Name 2");
        mockCompany2.setDescription("Description for Company 2");

        List<Software> mockSoftwareList = new ArrayList<>();
        Software mockSoftware1 = new Software();
        mockSoftware1.setName("SoftwareName1");
        mockSoftware1.setCompany(mockCompany1); 
        mockSoftware1.setNumberOfEmployees(50);
        mockSoftware1.setCost(1000);
        mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));
        mockSoftware1.setIsExpired(false);

        Software mockSoftware2 = new Software();
        mockSoftware2.setName("SoftwareName2");
        mockSoftware2.setCompany(mockCompany2); 
        mockSoftware2.setNumberOfEmployees(100);
        mockSoftware2.setCost(2000);
        mockSoftware2.setExpiryDate(LocalDate.now().plusMonths(12));
        mockSoftware2.setIsExpired(false);

        when(softwareService.getSoftwares()).thenReturn(mockSoftwareList);

        List<Software> result = softwareController.getSoftwares();

        verify(softwareService).getSoftwares();

        assertEquals(mockSoftwareList, result);

    }
}
