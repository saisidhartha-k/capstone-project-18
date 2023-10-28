package com.capstone.licencelifecyclemanagement.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;
import com.capstone.licencelifecyclemanagement.entitys.Notification;

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.DecommisionedItemRepository;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;
import com.capstone.licencelifecyclemanagement.services.SoftwareCompanyService;
import com.capstone.licencelifecyclemanagement.services.SoftwarePurchaseService;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SoftwareServiceTest {

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

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private DecommisionedItemRepository decommisionedItemRepository;

    private SoftwareCompany mockCompany1;
    private Software mockSoftware1;
    private SoftwarePurchaseId purchaseId1;
    private SoftwarePurchase purchase1;

    @BeforeEach
    void setUp() {
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

        purchaseId1 = new SoftwarePurchaseId();
        purchaseId1.setLicenseNumber("License1");
        purchaseId1.setSoftware(mockSoftware1);

        purchase1 = new SoftwarePurchase(purchaseId1);
        purchase1.setPurchaseDate(LocalDate.now());

    }

    @Test
    void testGetSoftware() {

        List<Software> mockSoftwareList = new ArrayList<>();

        Mockito.when(softwareRepository.findAll()).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.getSoftware();

        Mockito.verify(softwareRepository).findAll();

        assertEquals(mockSoftwareList, result);
    }

    @Test
    void testAddSoftware() {

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
    void testNotExpList() {
        List<Software> mockSoftwareList = new ArrayList<>();
        mockSoftware1.setExpiryDate(LocalDate.now().plusDays(10));
        mockSoftwareList.add(mockSoftware1);
        LocalDate today = LocalDate.now();

        when(softwareRepository.findNonExpiredSoftware(today)).thenReturn(mockSoftwareList); // Correct method name

        List<Software> result = softwareService.notExpList(); // Correct method name

        verify(softwareRepository, times(1)).findNonExpiredSoftware(today);

        assertEquals(1, result.size());
    }

    @Test
    void testExpiredSoftwares() {

        List<Software> mockSoftwareList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        mockSoftwareList.add(mockSoftware1);

        Mockito.when(softwareRepository.findExpiredSoftware(today)).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.expiredSoftwares();

        Mockito.verify(softwareRepository).findExpiredSoftware(today);

        assertEquals(1, result.size());
    }

    @Test
    void testAboutToExpireSoftware() {

        mockSoftware1.setExpiryDate(LocalDate.now().plusDays(10));

        List<Software> mockSoftwareList = new ArrayList<>();
        mockSoftwareList.add(mockSoftware1);

        when(softwareService.getSoftwares()).thenReturn(mockSoftwareList);

        List<Software> result = softwareService.aboutToExpire();

        assertEquals(1, result.size());
        assertEquals(mockSoftware1, result.get(0));

    }

    @Test
    void testSoftwareAboutToExpireCount() {

        mockSoftware1.setExpiryDate(LocalDate.now().plusDays(10));

        List<Software> mockSoftwareList = new ArrayList<>();
        mockSoftwareList.add(mockSoftware1);

        when(softwareService.aboutToExpire()).thenReturn(mockSoftwareList);

        int result = softwareService.aboutToExpireCount();

        assertEquals(1, result);
    }

    @Test
    void testNotExpiredSoftwaresCount() {

        List<Software> mockSoftwareList = new ArrayList<>();

        LocalDate today = LocalDate.now();

        mockSoftwareList.add(mockSoftware1);

        Mockito.when(softwareRepository.findNonExpiredSoftware(today)).thenReturn(mockSoftwareList);

        int result = softwareService.notExpListCount();

        Mockito.verify(softwareRepository).findNonExpiredSoftware(today);

        assertEquals(1, result);
    }

    @Test
    void testExpiredSoftwaresCount() {

        List<Software> mockSoftwareList = new ArrayList<>();
        Software mockSoftware1 = new Software();
        mockSoftware1.setId(1);

        Software mockSoftware2 = new Software();
        mockSoftware2.setId(2);

        Software mockSoftware3 = new Software();
        mockSoftware3.setId(3);

        mockSoftwareList.add(mockSoftware1);
        mockSoftwareList.add(mockSoftware2);
        mockSoftwareList.add(mockSoftware3);
        LocalDate today = LocalDate.now();

        Mockito.when(softwareRepository.findExpiredSoftware(today)).thenReturn(mockSoftwareList);

        int result = softwareService.expiredSoftwaresCount();

        Mockito.verify(softwareRepository).findExpiredSoftware(today);

        assertEquals(3, result);
    }

    @Test
    void testGetSoftwareCompanies() {

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
    void testAddSoftwareelse() {

        Software mockSoftware1 = new Software();
        mockSoftware1.setName("SoftwareName1");
        mockSoftware1.setNumberOfEmployees(50);
        mockSoftware1.setCost(1000);
        mockSoftware1.setExpiryDate(LocalDate.now().plusMonths(6));
        mockSoftware1.setPurchaseDate(LocalDate.now());
        mockSoftware1.setId(1);

        SoftwareCompany mockCompany = new SoftwareCompany();
        mockCompany.setId(999);
        mockCompany.setName("NonExistentCompany");
        mockSoftware1.setCompany(mockCompany);

        Mockito.when(softwareCompanyRepository.existsById(mockCompany.getId())).thenReturn(false);

        Mockito.when(softwareRepository.save(Mockito.any())).thenReturn(mockSoftware1);
        Mockito.when(softwarePurchaseRepository.save(Mockito.any())).thenReturn(new SoftwarePurchase());

        Software addedSoftware = softwareService.addSoftware(mockSoftware1);

        Mockito.verify(softwareCompanyRepository).existsById(mockCompany.getId());

        Mockito.verify(softwareRepository).save(Mockito.any());
        Mockito.verify(softwarePurchaseRepository).save(Mockito.any());

        assertEquals("SoftwareName1", addedSoftware.getName());
    }

    @Test
    void testAssetCheck() {
        List<Software> softwareList = new ArrayList<>();

        Software expiringSoftware = new Software();
        expiringSoftware.setName("Expiring Software");
        expiringSoftware.setExpiryDate(LocalDate.now().plusDays(25));
        softwareList.add(expiringSoftware);

        Software nonExpiringSoftware = new Software();
        nonExpiringSoftware.setName("Non-Expiring Software");
        nonExpiringSoftware.setExpiryDate(LocalDate.now().plusDays(35));
        softwareList.add(nonExpiringSoftware);

        Mockito.when(softwareRepository.findAll()).thenReturn(softwareList);

        List<String> notificationList = softwareService.assetCheck();

        assert notificationList.size() == 1;
        assert notificationList.get(0).contains("Expiring Software");
        assertTrue(notificationList.get(0).contains("Expiring Software"));

    }

    @Test
    void testCalculateRemainingDays() {
        SoftwareService softwareService = new SoftwareService();
        LocalDate expiryDate = LocalDate.now();

        int remainingDays = softwareService.calculateRemainingDays(expiryDate);

        assertEquals(0, remainingDays);
    }

    @Test
    void testDecommissionSoftware() {
        int id = 1;
        Software software = new Software();
        software.setId(id);
        software.setName("Test Software");

        SoftwarePurchaseId softwarePurchaseId = new SoftwarePurchaseId();
        softwarePurchaseId.setSoftware(software);
        softwarePurchaseId.setLicenseNumber("12345");

        SoftwarePurchase softwarePurchase = new SoftwarePurchase();
        softwarePurchase.setSoftwarePurchaseId(softwarePurchaseId);
        softwarePurchase.setPurchaseDate(LocalDate.now());

        List<SoftwarePurchase> softwarePurchases = Arrays.asList(softwarePurchase);

        when(softwareRepository.findById(id)).thenReturn(Optional.of(software));
        when(softwarePurchaseRepository.findBySoftwarePurchaseId_Software_Id(id)).thenReturn(softwarePurchases);

        softwareService.decommissionSoftware(id);

        verify(decommisionedItemRepository, times(1)).save(any(DecommissionedItem.class));
        verify(softwarePurchaseRepository, times(1)).delete(softwarePurchase);
        verify(softwareRepository, times(1)).deleteById(id);
    }

    @Test
    void testSetExistingCompany() {
        int companyId = 1;
        Software software = new Software();
        SoftwareCompany company = new SoftwareCompany();
        company.setId(companyId);

        when(softwareCompanyRepository.findById(companyId)).thenReturn(Optional.of(company));

        softwareService.setExistingCompany(software, company);

        assertEquals(company, software.getCompany());
    }

    @Test
    void testPercentageOfSoftwareAboutToExpire() {
        SoftwareService softwareService = new SoftwareService() {
            @Override
            public List<Software> aboutToExpire() {
                return Arrays.asList(new Software(), new Software());
            }

            @Override
            public int getTotalSoftwareCount() {
                return 5;
            }
        };

        int percentage = softwareService.percentageOfSoftwareAboutToExpire();

        assertEquals(40, percentage);
    }

    @Test
    void testPercentageOfNotExpiredSoftware() {
        SoftwareService softwareService = new SoftwareService() {
            @Override
            public int notExpListCount() {
                return 2;
            }

            @Override
            public int getTotalSoftwareCount() {
                return 5;
            }
        };

        int percentage = softwareService.percentageOfNotExpiredSoftware();

        assertEquals(40, percentage);
    }

    @Test
    void testPercentageOfExpiredSoftware() {
        SoftwareService softwareService = new SoftwareService() {
            @Override
            public int expiredSoftwaresCount() {
                return 2;
            }

            @Override
            public int getTotalSoftwareCount() {
                return 5;
            }
        };

        int percentage = softwareService.percentageOfExpiredSoftware();

        assertEquals(40, percentage);
    }

}