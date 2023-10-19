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

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.repository.DeviceCompanyRepository;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.NotificationRepository;
import com.capstone.licencelifecyclemanagement.services.DeviceService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

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
        mockDevice.setIsExpired(false);
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
        assertFalse(addedDevice.getIsExpired());
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

        // Mock the save method to return the device
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        // Mock the save method for the device purchase
        when(devicePurchaseRepository.save(any(DevicePurchase.class))).thenReturn(null);

        String result = deviceService.renewDevice(1, deviceDto);

        // Verify that findById, save, and save for device purchase were called
        verify(deviceRepository, times(1)).findById(1);
        verify(deviceRepository, times(1)).save(any(Device.class));
        verify(devicePurchaseRepository, times(1)).save(any(DevicePurchase.class));

        assertEquals("Device renewed: 1", result);
    }

    @Test
    public void testNotExpiredDevice() {
        List<Device> mockDeviceList = new ArrayList<>();

        mockDeviceList.add(mockDevice);

        when(deviceRepository.findByIsExpired(false)).thenReturn(mockDeviceList);

        List<Device> result = deviceService.notExpiredDevice();

        verify(deviceRepository, times(1)).findByIsExpired(false);

        assertEquals(1, result.size());
    }

    @Test
    public void testExpiredDevice() {

        List<Device> mockDeviceList = new ArrayList<>();
        mockDevice.setIsExpired(true);

        mockDeviceList.add(mockDevice);

        Mockito.when(deviceRepository.findByIsExpired(true)).thenReturn(mockDeviceList);

        List<Device> result = deviceService.expierdDevices();

        Mockito.verify(deviceRepository).findByIsExpired(true);

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
    public void testDeviceAboutToExpireCount() {

        mockDevice.setExpiryDate(LocalDate.now().plusDays(10));

        List<Device> mockDeviceList = new ArrayList<>();
        mockDeviceList.add(mockDevice);

        when(deviceService.devicesAboutToExpire()).thenReturn(mockDeviceList);

        int result = deviceService.devicesAboutToExpireCount();

        assertEquals(1, result);
    }

    @Test
    public void testNotExpiredDevicesCount() {

        List<Device> mockDeviceList = new ArrayList<>();

        mockDevice.setIsExpired(true);

        mockDeviceList.add(mockDevice);

        Mockito.when(deviceRepository.findByIsExpired(false)).thenReturn(mockDeviceList);

        int result = deviceService.devicesNotExpiredCount();

        Mockito.verify(deviceRepository).findByIsExpired(false);

        assertEquals(1, result);
    }

    @Test
    public void testExpiredDeviceCount() {

        List<Device> mockDeviceList = new ArrayList<>();
        Device mockDevice1 = new Device();
        mockDevice1.setId(1);
        mockDevice1.setIsExpired(false);

        Device mockDevice2 = new Device();
        mockDevice2.setId(2);
        mockDevice2.setIsExpired(true);

        Device mockDevice3 = new Device();
        mockDevice3.setId(3);
        mockDevice3.setIsExpired(true);

        mockDeviceList.add(mockDevice1);
        mockDeviceList.add(mockDevice2);
        mockDeviceList.add(mockDevice3);

        Mockito.when(deviceRepository.findByIsExpired(true)).thenReturn(mockDeviceList);

        int result = deviceService.expiredDevicesCount();

        Mockito.verify(deviceRepository).findByIsExpired(true);

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
    public void testDeviceSendNotification() {
        // Arrange
        Device device = new Device();
        device.setName("Test device");
        device.setExpiryDate(LocalDate.now().plusDays(20));

       
    int remainingDays = 20;

    // Act
    deviceService.sendNotification(remainingDays, device);

    // Assert
    verify(notificationRepository, times(1)).save(any());
    }

}