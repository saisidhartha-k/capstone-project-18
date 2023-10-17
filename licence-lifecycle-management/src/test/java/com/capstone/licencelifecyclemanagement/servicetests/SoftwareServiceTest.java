package com.capstone.licencelifecyclemanagement.servicetests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.repository.SoftwareCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SoftwareServiceTest {

    @InjectMocks
    private SoftwareService softwareService;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private SoftwareCompanyRepository softwareCompanyRepository;

    @Mock
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    @Test
    public void testGetSoftware() {

        SoftwareCompany mockCompany1 = new SoftwareCompany();
        mockCompany1.setName("Company Name 1");
        mockCompany1.setDescription("Description for Company 1");

        List<Software> mockSoftwareList = new ArrayList<>();
        Software mockSoftware1 = new Software();
        mockSoftware1.setName("SoftwareName1");
        mockSoftware1.setCompany(mockCompany1);
        mockSoftware1.setNumberOfEmployees(50);
        mockSoftware1.setCost(1000);
        mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));
        mockSoftware1.setIsExpired(false);
        Mockito.when(softwareRepository.findAll()).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.getSoftware();

        Mockito.verify(softwareRepository).findAll();

        assertEquals(mockSoftwareList, result);
    }

    @Test
    public void testAddSoftware() {
        Software mockSoftware = new Software();
        mockSoftware.setName("TestSoftware");
        mockSoftware.setCompany(new SoftwareCompany());
        mockSoftware.setNumberOfEmployees(50);
        mockSoftware.setCost(1000);
        mockSoftware.setExpiryDate(LocalDate.now().plusMonths(6));
        mockSoftware.setIsExpired(false);

        Mockito.when(softwareCompanyRepository.existsById(Mockito.anyInt())).thenReturn(true);

        Mockito.when(softwareCompanyRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new SoftwareCompany()));

        Mockito.when(softwareRepository.save(Mockito.any())).thenReturn(mockSoftware);

        Mockito.when(softwarePurchaseRepository.save(Mockito.any())).thenReturn(new SoftwarePurchase());

        Software addedSoftware = softwareService.addSoftware(mockSoftware);

        Mockito.verify(softwareCompanyRepository).existsById(Mockito.anyInt());
        Mockito.verify(softwareCompanyRepository).findById(Mockito.anyInt());
        Mockito.verify(softwareRepository).save(Mockito.any());
        Mockito.verify(softwarePurchaseRepository).save(Mockito.any());

        assertEquals("TestSoftware", addedSoftware.getName());
    }

    @Test
    public void testRenewSoftware() {

    // Create a SoftwareDto with required data
    SoftwareDto softwareDto = new SoftwareDto();
    softwareDto.setCost(100); // Set the cost
    softwareDto.setExpiryDate(LocalDate.now().plusMonths(1)); // Set the expiry date

    // Create a mock Software instance and SoftwareCompany
    Software software = new Software();
    software.setId(1);
    software.setName("Sample Software");
    software.setCost(50); // Existing cost
    software.setExpiryDate(LocalDate.now().plusDays(10)); // Existing expiry date

    SoftwareCompany company = new SoftwareCompany();
    company.setId(1);
    company.setName("Sample Company");

    // Mock repository methods
    when(softwareRepository.findById(1)).thenReturn(Optional.of(software));
   // when(softwareCompanyRepository.existsById(1)).thenReturn(true); // Mock to return true

    // Mock save methods
    when(softwareRepository.save(any(Software.class))).thenReturn(software);
    when(softwarePurchaseRepository.save(any(SoftwarePurchase.class))).thenReturn(null);

    // Call the renewSoftware method
    String result = softwareService.renewSoftware(1, softwareDto);

    // Assertions
    verify(softwareRepository, times(1)).findById(1);
   // verify(softwareCompanyRepository, times(1)).existsById(1);
    verify(softwareRepository, times(1)).save(any(Software.class));
    verify(softwarePurchaseRepository, times(1)).save(any(SoftwarePurchase.class));

    // Verify that the returned result is as expected
    assertEquals("Software renewed: 1", result);
}
}
