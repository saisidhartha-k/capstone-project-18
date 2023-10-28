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

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchaseId;
import com.capstone.licencelifecyclemanagement.repository.DecommisionedItemRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;
import com.capstone.licencelifecyclemanagement.services.DeviceService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DevicePurchaseRepository devicePurchaseRepository;

    @Mock
    private DeviceCompanyRepository deviceCompanyRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private DecommisionedItemRepository decommisionedItemRepository;

    private Device mockDevice;
    private DeviceCompany mockCompany;

    @BeforeEach
    void setUp() {
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
    void testGetDevice() {

        List<Device> mockDeviceList = new ArrayList<>();
        mockDeviceList.add(mockDevice);
        Mockito.when(deviceRepository.findAll()).thenReturn(mockDeviceList);

        List<Device> result = deviceService.getDevices();

        Mockito.verify(deviceRepository).findAll();

        assertEquals(mockDeviceList, result);
    }

    @Test
    void testAddDevice() {

        Mockito.when(deviceCompanyRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(deviceCompanyRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new DeviceCompany()));

        Mockito.when(deviceRepository.save(Mockito.any())).thenReturn(mockDevice);

        Mockito.when(devicePurchaseRepository.save(Mockito.any())).thenReturn(new DevicePurchase());

        Device addedDevice = deviceService.addDevice(mockDevice);

        Mockito.verify(deviceCompanyRepository).existsById(Mockito.anyInt());
        Mockito.verify(deviceCompanyRepository).findById(Mockito.anyInt());
        Mockito.verify(deviceRepository).save(Mockito.any());
        Mockito.verify(devicePurchaseRepository).save(Mockito.any());

        assertEquals("devicename1", addedDevice.getName());
        assertEquals(50, addedDevice.getNumberOfEmployees());
        assertEquals(1000, addedDevice.getCost());
        assertEquals(LocalDate.now().plusMonths(6), addedDevice.getExpiryDate());
        assertEquals(LocalDate.now(), addedDevice.getPurchaseDate());
        assertEquals(1, addedDevice.getId());
    }

    @Test
    void testRenewDevice() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setCost(100);
        deviceDto.setExpiryDate(LocalDate.now().plusMonths(1));
        Device device = new Device();
        device.setId(1);
        device.setName("Sample Device");
        device.setCost(50);
        device.setExpiryDate(LocalDate.now().plusDays(10));

        DeviceCompany company = new DeviceCompany();
        company.setId(1);
        company.setName("Sample Company");

        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));

        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        when(devicePurchaseRepository.save(any(DevicePurchase.class))).thenReturn(null);

        String result = deviceService.renewDevice(1, deviceDto);

        verify(deviceRepository, times(1)).findById(1);
        verify(deviceRepository, times(1)).save(any(Device.class));
        verify(devicePurchaseRepository, times(1)).save(any(DevicePurchase.class));

        assertEquals("Device renewed: 1", result);
    }

    @Test
    void testNotExpiredDevice() {
        List<Device> mockDeviceList = new ArrayList<>();
        mockDevice.setExpiryDate(LocalDate.now().plusDays(10));
        mockDeviceList.add(mockDevice);
        LocalDate today = LocalDate.now();

        when(deviceRepository.findNonExpiredDevice(today)).thenReturn(mockDeviceList);

        List<Device> result = deviceService.notExpiredDevice();

        verify(deviceRepository, times(1)).findNonExpiredDevice(today);

        assertEquals(1, result.size());
    }

    @Test
    void testExpiredDevice() {
        LocalDate today = LocalDate.now();

        List<Device> mockDeviceList = new ArrayList<>();

        mockDeviceList.add(mockDevice);

        Mockito.when(deviceRepository.findExpiredDevice(today)).thenReturn(mockDeviceList);

        List<Device> result = deviceService.expierdDevices();

        Mockito.verify(deviceRepository).findExpiredDevice(today);

        assertEquals(1, result.size());
    }

    @Test
    void testAboutToExpireDevice() {

        mockDevice.setExpiryDate(LocalDate.now().plusDays(10));

        List<Device> mockDeviceList = new ArrayList<>();
        mockDeviceList.add(mockDevice);

        Mockito.when(deviceService.getDevices()).thenReturn(mockDeviceList);

        List<Device> result = deviceService.devicesAboutToExpire();

        assertEquals(1, result.size());
        assertEquals(mockDevice, result.get(0));

    }

    @Test
    void testDeviceAboutToExpireCount() {

        mockDevice.setExpiryDate(LocalDate.now().plusDays(10));

        List<Device> mockDeviceList = new ArrayList<>();
        mockDeviceList.add(mockDevice);

        when(deviceService.devicesAboutToExpire()).thenReturn(mockDeviceList);

        int result = deviceService.devicesAboutToExpireCount();

        assertEquals(1, result);
    }

    @Test
    void testNotExpiredDevicesCount() {
        List<Device> mockDeviceList = new ArrayList<>();
        mockDevice.setExpiryDate(LocalDate.now().plusDays(10));
        mockDeviceList.add(mockDevice);
        LocalDate today = LocalDate.now();

        when(deviceRepository.findNonExpiredDevice(today)).thenReturn(mockDeviceList);

        int result = deviceService.devicesNotExpiredCount(); // Get the size of the returned list

        verify(deviceRepository, times(1)).findNonExpiredDevice(today);

        assertEquals(1, result);
    }

    @Test
    void testExpiredDeviceCount() {

        List<Device> mockDeviceList = new ArrayList<>();
        Device mockDevice1 = new Device();
        mockDevice1.setId(1);

        Device mockDevice2 = new Device();
        mockDevice2.setId(2);

        Device mockDevice3 = new Device();
        mockDevice3.setId(3);

        mockDeviceList.add(mockDevice1);
        mockDeviceList.add(mockDevice2);
        mockDeviceList.add(mockDevice3);

        LocalDate today = LocalDate.now();

        Mockito.when(deviceRepository.findExpiredDevice(today)).thenReturn(mockDeviceList);

        int result = deviceService.expiredDevicesCount();

        Mockito.verify(deviceRepository).findExpiredDevice(today);

        assertEquals(3, result);
    }

    @Test
    void testDeviceAssetCheck() {
        Device device = new Device();
        device.setExpiryDate(LocalDate.now().plusDays(15));

        when(deviceService.getDevices()).thenReturn(Collections.singletonList(device));

        deviceService.assetCheck();

    }

    @Test
    void testDecommissionDevice() {
        int id = 1;
        Device device = new Device();
        device.setId(id);
        device.setName("Test Device");

        DevicePurchaseId devicePurchaseId = new DevicePurchaseId();
        devicePurchaseId.setDevice(device);
        devicePurchaseId.setLicenseNumber("12345");

        DevicePurchase devicePurchase = new DevicePurchase();
        devicePurchase.setDevicePurchaseId(devicePurchaseId);
        devicePurchase.setPurchaseDate(LocalDate.now());

        List<DevicePurchase> devicePurchases = Arrays.asList(devicePurchase);

        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
        when(devicePurchaseRepository.findByDevicePurchaseId_Device_Id(id)).thenReturn(devicePurchases);

        deviceService.decomissionDevice(id);

        verify(decommisionedItemRepository, times(1)).save(any(DecommissionedItem.class));
        verify(devicePurchaseRepository, times(1)).delete(devicePurchase);
        verify(deviceRepository, times(1)).deleteById(id);
    }

    @Test
    void testCreateNewCompany() {
        Device device = new Device();
        DeviceCompany company = new DeviceCompany();
        device.setCompany(company);

        when(deviceCompanyRepository.save(company)).thenReturn(company);

        deviceService.createNewCompany(device);

        assertEquals(company, device.getCompany());
    }

    @Test
    void testSetExistingCompany() {
        int companyId = 1;
        Device device = new Device();
        DeviceCompany company = new DeviceCompany();
        company.setId(companyId);

        when(deviceCompanyRepository.findById(companyId)).thenReturn(Optional.of(company));

        deviceService.setExistingCompany(device, company);

        assertEquals(company, device.getCompany());
    }

    @Test
    void testPercentageOfDevicesAboutToExpire() {

        DeviceService deviceService = new DeviceService() {
            @Override
            public List<Device> devicesAboutToExpire() {
                return Arrays.asList(new Device(), new Device());
            }

            @Override
            public int getTotalDeviceCount() {
                return 5;
            }
        };

        int percentage = deviceService.percentageOfDevicesAboutToExpire();

        assertEquals(40, percentage);
    }

    @Test
    void testPercentageOfDevicesAboutToExpire0() {
        DeviceService deviceService = new DeviceService() {
            @Override
            public List<Device> devicesAboutToExpire() {
                return Arrays.asList(new Device(), new Device());
            }

            @Override
            public int getTotalDeviceCount() {
                return 0;
            }
        };

        int percentage = deviceService.percentageOfDevicesAboutToExpire();

        assertEquals(0, percentage);
    }

    @Test
    void testPercentageOfNotExpiredDevices() {
        DeviceService deviceService = new DeviceService() {
            @Override
            public List<Device> notExpiredDevice() {
                return Arrays.asList(new Device(), new Device());
            }

            @Override
            public int getTotalDeviceCount() {
                return 5;
            }
        };

        int percentage = deviceService.percentageOfNotExpiredDevices();

        assertEquals(40, percentage);
    }

    @Test
    void testPercentageOfExpiredDevices() {
        DeviceService deviceService = new DeviceService() {
            @Override
            public List<Device> expierdDevices() {
                return Arrays.asList(new Device(), new Device());
            }

            @Override
            public int getTotalDeviceCount() {
                return 5;
            }
        };

        int percentage = deviceService.percentageOfExpiredDevices();

        assertEquals(40, percentage);
    }

}