package com.capstone.licencelifecyclemanagement.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.RMARepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;
import com.capstone.licencelifecyclemanagement.services.RmaService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RmaServiceTest {

    @InjectMocks
    private RmaService rmaService;

    @Mock
    private RMARepository rmaRepository;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    @Mock
    private DevicePurchaseRepository devicePurchaseRepository;

    private SoftwareCompany mockCompany1;
    private Software mockSoftware1;
    private SoftwarePurchaseId purchaseId1;
    private SoftwarePurchase purchase1;
    private Device mockDevice;
    private DeviceCompany mockCompany;

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

        mockCompany = new DeviceCompany();
        mockCompany.setId(1);
        mockCompany.setName("Company Name 1");
        mockCompany.setDescription("Description for Company 1");

        mockDevice = new Device();
        mockDevice.setId(1);
        mockDevice.setName("devicename1");
        mockDevice.setCompany(mockCompany);
        mockDevice.setNumberOfEmployees(50);
        mockDevice.setCost(1000);
        mockDevice.setExpiryDate(LocalDate.now().plusMonths(6));
        mockDevice.setLocation("hyderabad");

    }

    @Test
    void testMoveToRmaWithSoftware() {
        // Given
        int id = 1;
        RMA rma = new RMA();
        rma.setProductType("Software");
        rma.setReason("defect");
        rma.setId(1);
        rma.setRequestDate(LocalDate.now());

        when(softwareRepository.findById(id)).thenReturn(Optional.of(mockSoftware1));

        // When
        rmaService.moveToRma(id, rma);

        // Then
        verify(rmaRepository, times(1)).save(any(RMA.class));
        verify(softwarePurchaseRepository, times(1)).deleteBySoftwarePurchaseId_Software_Id(id);
        verify(softwareRepository, times(1)).deleteById(id);
        ArgumentCaptor<RMA> rmaCaptor = ArgumentCaptor.forClass(RMA.class);
        verify(rmaRepository).save(rmaCaptor.capture());
        RMA savedRMA = rmaCaptor.getValue();
        assertEquals(mockCompany1.getId(), savedRMA.getCompanyId());
        assertEquals(mockCompany1.getName(), savedRMA.getCompanyName());
        assertEquals(mockSoftware1.getExpiryDate(), savedRMA.getExpiryDate());
        assertEquals(mockSoftware1.getLicenseNumber(), savedRMA.getLicenseNumber());
        assertEquals(mockSoftware1.getName(), savedRMA.getProductName());
        assertEquals(mockSoftware1.getCost(), savedRMA.getCost());
        assertEquals(mockSoftware1.getNumberOfEmployees(), savedRMA.getNumberOfEmployees());
        assertEquals(rma.getId(),savedRMA.getId());
        assertEquals(rma.getReason(),savedRMA.getReason());
        assertEquals(mockSoftware1.getPurchaseDate(),savedRMA.getPurchasDate());
        assertEquals(rma.getRequestDate(),savedRMA.getRequestDate());

    }

    @Test
    void testMoveToRmaWithDevice() {
        int id = 1;
        RMA rma = new RMA();
        rma.setProductType("Device");
        rma.setReason("defect");

        when(deviceRepository.findById(id)).thenReturn(Optional.of(mockDevice));

        // When
        rmaService.moveToRma(id, rma);

        // Then
        verify(rmaRepository, times(1)).save(any(RMA.class));
        verify(devicePurchaseRepository, times(1)).deleteByDevicePurchaseId_Device_Id(id);
        verify(deviceRepository, times(1)).deleteById(id);
    }

    @Test
    void testPutBackFromRmaWithSoftware() {
        // Given
        int rmaId = 1;
        RMA rma = new RMA();
        rma.setProductType("Software");
        rma.setReason("defect");
        rma.setProductName(mockSoftware1.getName());
        rma.setCompanyId(mockCompany1.getId());
        rma.setExpiryDate(mockSoftware1.getExpiryDate());
        rma.setLicenseNumber(purchaseId1.getLicenseNumber());
        rma.setNumberOfEmployees(mockSoftware1.getNumberOfEmployees());
        rma.setCost(mockSoftware1.getCost());

        when(rmaRepository.findById(rmaId)).thenReturn(Optional.of(rma));

        // When
        rmaService.putBackFromRma(rmaId);

        // Then
        verify(softwareRepository, times(1)).save(any(Software.class));
        verify(softwarePurchaseRepository, times(1)).save(any(SoftwarePurchase.class));
        verify(rmaRepository, times(1)).deleteById(rmaId);
    }

    @Test
    void testPutBackFromRmaWithDevice() {
        // Given
        int rmaId = 2;
        RMA rma = new RMA();
        rma.setProductType("Device");
        rma.setReason("defect");
        rma.setProductName(mockDevice.getName());
        rma.setCompanyId(mockCompany.getId());
        rma.setExpiryDate(mockDevice.getExpiryDate());
        rma.setLicenseNumber(mockDevice.getLicenseNumber());
        rma.setCost(mockDevice.getCost());

        when(rmaRepository.findById(rmaId)).thenReturn(Optional.of(rma));

        rmaService.putBackFromRma(rmaId);

        verify(deviceRepository, times(1)).save(any(Device.class));
        verify(devicePurchaseRepository, times(1)).save(any(DevicePurchase.class));
        verify(rmaRepository, times(1)).deleteById(rmaId);
    }

}
