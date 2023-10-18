package com.capstone.licencelifecyclemanagement.servicetests;

import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.SoftwareCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;
import com.capstone.licencelifecyclemanagement.services.SoftwareCompanyService;
import com.capstone.licencelifecyclemanagement.services.SoftwarePurchaseService;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SoftwareServiceTest {

    @InjectMocks
    private SoftwareService softwareService;

    @InjectMocks
    private SoftwareCompanyService softwareCompanyService;

    @InjectMocks
    private SoftwarePurchaseService softwarePurchaseService;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private SoftwareCompanyRepository softwareCompanyRepository;

    @Mock
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    private SoftwareCompany mockCompany1;
    private Software mockSoftware1;
    private SoftwarePurchaseId purchaseId1;
    private SoftwarePurchase purchase1;

    @BeforeEach
    public void setUp() {
        mockCompany1 = new SoftwareCompany();
        mockCompany1.setId(1);
        mockCompany1.setName("Company Name 1");
        mockCompany1.setDescription("Description for Company 1");

        mockSoftware1 = new Software();
        mockSoftware1.setId(1);
        mockSoftware1.setName("SoftwareName1");
        mockSoftware1.setCompany(mockCompany1);
        mockSoftware1.setNumberOfEmployees(50);
        mockSoftware1.setCost(1000);
        mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));
        mockSoftware1.setIsExpired(false);

        purchaseId1 = new SoftwarePurchaseId();
        purchaseId1.setLicenseNumber("License1");
        purchaseId1.setSoftware(mockSoftware1);

        purchase1 = new SoftwarePurchase(purchaseId1);
        purchase1.setPurchaseDate(LocalDate.now());

    }

    @Test
    public void testGetSoftware() {

        SoftwareCompany mockCompany1 = new SoftwareCompany();

        List<Software> mockSoftwareList = new ArrayList<>();

        Mockito.when(softwareRepository.findAll()).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.getSoftware();

        Mockito.verify(softwareRepository).findAll();

        assertEquals(mockSoftwareList, result);
    }

    @Test
    public void testAddSoftware() {

        Mockito.when(softwareCompanyRepository.existsById(Mockito.anyInt())).thenReturn(true);

        Mockito.when(softwareCompanyRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new SoftwareCompany()));

        Mockito.when(softwareRepository.save(Mockito.any())).thenReturn(mockSoftware1);

        Mockito.when(softwarePurchaseRepository.save(Mockito.any())).thenReturn(new SoftwarePurchase());

        Software addedSoftware = softwareService.addSoftware(mockSoftware1);
        SoftwareCompany company = addedSoftware.getCompany();
        System.out.println(company.getName());

        Mockito.verify(softwareCompanyRepository).existsById(Mockito.anyInt());
        Mockito.verify(softwareCompanyRepository).findById(Mockito.anyInt());
        Mockito.verify(softwareRepository).save(Mockito.any());
        Mockito.verify(softwarePurchaseRepository).save(Mockito.any());

        assertEquals("SoftwareName1", addedSoftware.getName());
        assertEquals(50, addedSoftware.getNumberOfEmployees());
        assertEquals(1000, addedSoftware.getCost());
        assertEquals(LocalDate.now().plusMonths(6), addedSoftware.getExpiryDate());
        assertFalse(addedSoftware.getIsExpired());
        assertEquals(LocalDate.now(), addedSoftware.getPurchaseDate());
        assertEquals(1, addedSoftware.getId());

    }

    @Test
    void testRenewSoftware() {

        SoftwareDto softwareDto = new SoftwareDto();
        softwareDto.setCost(100);
        softwareDto.setExpiryDate(LocalDate.now().plusMonths(1));
        Software software = new Software();
        software.setId(1);
        software.setName("Sample Software");
        software.setCost(50);
        software.setExpiryDate(LocalDate.now().plusDays(10));

        SoftwareCompany company = new SoftwareCompany();
        company.setId(1);
        company.setName("Sample Company");

        when(softwareRepository.findById(1)).thenReturn(Optional.of(software));

        when(softwareRepository.save(any(Software.class))).thenReturn(software);
        when(softwarePurchaseRepository.save(any(SoftwarePurchase.class))).thenReturn(null);

        String result = softwareService.renewSoftware(1, softwareDto);

        verify(softwareRepository, times(1)).findById(1);
        verify(softwareRepository, times(1)).save(any(Software.class));
        verify(softwarePurchaseRepository, times(1)).save(any(SoftwarePurchase.class));

        assertEquals("Software renewed: 1", result);
    }

    @Test
    public void testNotExpList() {
        List<Software> mockSoftwareList = new ArrayList<>();

        mockSoftwareList.add(mockSoftware1);

        Mockito.when(softwareRepository.findByIsExpired(false)).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.notExpList();

        Mockito.verify(softwareRepository).findByIsExpired(false);

        assertEquals(1, result.size());
    }

    @Test
    public void testExpiredSoftwares() {

        List<Software> mockSoftwareList = new ArrayList<>();
        mockSoftware1.setIsExpired(true);

        mockSoftwareList.add(mockSoftware1);

        Mockito.when(softwareRepository.findByIsExpired(true)).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.expiredSoftwares();

        Mockito.verify(softwareRepository).findByIsExpired(true);

        assertEquals(1, result.size());
    }

    @Test
    public void testAboutToExpireSoftware() {

        mockSoftware1.setExpiryDate(LocalDate.now().plusDays(10));

        List<Software> mockSoftwareList = new ArrayList<>();
        mockSoftwareList.add(mockSoftware1);

        when(softwareService.getSoftwares()).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.aboutToExpire();

        assertEquals(1, result.size());
        assertEquals(mockSoftware1, result.get(0));

    }

    @Test
    public void testSoftwareAboutToExpireCount() {

        mockSoftware1.setExpiryDate(LocalDate.now().plusDays(10));

        List<Software> mockSoftwareList = new ArrayList<>();
        mockSoftwareList.add(mockSoftware1);

        when(softwareService.aboutToExpire()).thenReturn(mockSoftwareList);

        int result = softwareService.aboutToExpireCount();

        assertEquals(1, result);
    }

    @Test
    public void testNotExpiredSoftwaresCount() {

        List<Software> mockSoftwareList = new ArrayList<>();

        mockSoftware1.setIsExpired(true);

        mockSoftwareList.add(mockSoftware1);

        Mockito.when(softwareRepository.findByIsExpired(false)).thenReturn(mockSoftwareList);

        int result = softwareService.notExpListCount();

        Mockito.verify(softwareRepository).findByIsExpired(false);

        assertEquals(1, result);
    }

    @Test
    public void testExpiredSoftwaresCount() {

        List<Software> mockSoftwareList = new ArrayList<>();
        Software mockSoftware1 = new Software();
        mockSoftware1.setId(1);
        mockSoftware1.setIsExpired(false);

        Software mockSoftware2 = new Software();
        mockSoftware2.setId(2);
        mockSoftware2.setIsExpired(true);

        Software mockSoftware3 = new Software();
        mockSoftware3.setId(3);
        mockSoftware3.setIsExpired(true);

        mockSoftwareList.add(mockSoftware1);
        mockSoftwareList.add(mockSoftware2);
        mockSoftwareList.add(mockSoftware3);

        Mockito.when(softwareRepository.findByIsExpired(true)).thenReturn(mockSoftwareList);

        int result = softwareService.expiredSoftwaresCount();

        Mockito.verify(softwareRepository).findByIsExpired(true);

        assertEquals(3, result);
    }

    // @Test
    // public void testPercentageOfExpiredSoftware() {
    // List<Software> mockSoftwareList = new ArrayList<>();
    // Software mockSoftware1 = new Software();
    // mockSoftware1.setId(1);
    // mockSoftware1.setIsExpired(false);

    // Software mockSoftware2 = new Software();
    // mockSoftware2.setId(2);
    // mockSoftware2.setIsExpired(true);

    // Software mockSoftware3 = new Software();
    // mockSoftware3.setId(3);
    // mockSoftware3.setIsExpired(true);

    // mockSoftwareList.add(mockSoftware1);
    // mockSoftwareList.add(mockSoftware2);
    // mockSoftwareList.add(mockSoftware3);
    // when(softwareRepository.findAll()).thenReturn(mockSoftwareList);

    // when(softwareRepository.findByIsExpired(true)).thenReturn(mockSoftwareList);

    // //when(softwareService.getTotalSoftwareCount()).thenReturn(3);
    // assertEquals(softwareService.getTotalSoftwareCount(), 3);
    // assertEquals(softwareRepository.findByIsExpired(true), 2);

    // int result = softwareService.percentageOfExpiredSoftware();

    // //assertEquals(67, result);
    // }

    @Test
    public void testGetSoftwareCompanies() {

        List<SoftwareCompany> mockCompanyList = new ArrayList<>();
        mockCompanyList.add(mockCompany1);

        when(softwareCompanyRepository.findAll()).thenReturn(mockCompanyList);

        List<SoftwareCompany> softwareCompanies = softwareCompanyService.getSoftwareCompanies();

        assertEquals(1, softwareCompanies.size());
        assertEquals(1, softwareCompanies.get(0).getId());
        assertEquals("Company Name 1", softwareCompanies.get(0).getName());
        assertEquals("Description for Company 1", softwareCompanies.get(0).getDescription());
    }

    @Test
    void testGetAll() {

        List<SoftwarePurchase> mockPurchaseList = Arrays.asList(purchase1);

        when(softwarePurchaseRepository.findAll()).thenReturn(mockPurchaseList);

        List<SoftwarePurchase> purchases = softwarePurchaseService.getAll();

        assertEquals(1, purchases.size());

        assertEquals("License1", purchases.get(0).getSoftwarePurchaseId().getLicenseNumber());
        assertEquals("SoftwareName1", purchases.get(0).getSoftwarePurchaseId().getSoftware().getName());

    }

    @Test
    public void testAddSoftwareelse() {

        // Create a mock Software object
        Software mockSoftware1 = new Software();
        mockSoftware1.setName("SoftwareName1");
        mockSoftware1.setNumberOfEmployees(50);
        mockSoftware1.setCost(1000);
        mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));
        mockSoftware1.setIsExpired(false);
        mockSoftware1.setPurchaseDate(LocalDate.now());
        mockSoftware1.setId(1);

        // Create a mock SoftwareCompany object
        SoftwareCompany mockCompany = new SoftwareCompany();
        mockCompany.setId(999); // This ID should not exist in your repository
        mockCompany.setName("NonExistentCompany");
        mockSoftware1.setCompany(mockCompany);

        // Mock the existsById method to return false
        Mockito.when(softwareCompanyRepository.existsById(mockCompany.getId())).thenReturn(false);

        // Mock the save methods
        Mockito.when(softwareRepository.save(Mockito.any())).thenReturn(mockSoftware1);
        Mockito.when(softwarePurchaseRepository.save(Mockito.any())).thenReturn(new SoftwarePurchase());

        // Call the addSoftware method
        Software addedSoftware = softwareService.addSoftware(mockSoftware1);

        // Verify that existsById was called with the correct ID
        Mockito.verify(softwareCompanyRepository).existsById(mockCompany.getId());

        // Verify that save was called on the repositories
        Mockito.verify(softwareRepository).save(Mockito.any());
        Mockito.verify(softwarePurchaseRepository).save(Mockito.any());

        // Assert that the returned Software object has the expected values
        assertEquals("SoftwareName1", addedSoftware.getName());
    }

    // @Test
    // public void testRenewSoftwareif() {

    //     // Create a mock Software object
    //     Software mockSoftware1 = new Software();
    //     mockSoftware1.setName("SoftwareName1");
    //     mockSoftware1.setNumberOfEmployees(50);
    //     mockSoftware1.setCost(1000);
    //     mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));
    //     mockSoftware1.setIsExpired(false);
    //     mockSoftware1.setPurchaseDate(LocalDate.now());
    //     mockSoftware1.setId(1);

    //     // Create a mock SoftwareCompany object
    //     SoftwareCompany mockCompany = new SoftwareCompany();
    //     mockCompany.setId(999); // This ID should exist in your repository
    //     mockCompany.setName("ExistingCompany");
    //     mockSoftware1.setCompany(mockCompany);

    //     // Create a mock SoftwareDto object
    //     SoftwareDto dto = new SoftwareDto();
    //     dto.setCost(2000);
    //     dto.setExpiryDate(LocalDate.now().plusMonths(12));
    //     dto.setCompany(mockCompany);

    //     // Mock the existsById method to return true
    //     Mockito.when(softwareCompanyRepository.existsById(mockCompany.getId())).thenReturn(true);

    //     // Mock the findById method to return the existing software
    //     Mockito.when(softwareRepository.findById(mockSoftware1.getId())).thenReturn(Optional.of(mockSoftware1));

    //     // Mock the save methods
    //     Mockito.when(softwareRepository.save(Mockito.any())).thenReturn(mockSoftware1);
    //     Mockito.when(softwarePurchaseRepository.save(Mockito.any())).thenReturn(new SoftwarePurchase());

    //     // Call the renewSoftware method
    //     String result = softwareService.renewSoftware(mockSoftware1.getId(), dto);

    //     // Verify that existsById was called with the correct ID
    //     Mockito.verify(softwareCompanyRepository).existsById(mockCompany.getId());

    //     // Verify that save was called on the repositories
    //     Mockito.verify(softwareRepository).save(Mockito.any());
    //     Mockito.verify(softwarePurchaseRepository).save(Mockito.any());

    //     // Assert that the returned string is as expected
    //     assertEquals("Software renewed: " + mockSoftware1.getId(), result);
    // }
    
}