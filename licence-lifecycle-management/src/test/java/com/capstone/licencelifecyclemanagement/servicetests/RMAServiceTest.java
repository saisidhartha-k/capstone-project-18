package com.capstone.licencelifecyclemanagement.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.DeviceRepository;
import com.capstone.licencelifecyclemanagement.repository.RMARepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;
import com.capstone.licencelifecyclemanagement.repository.SoftwareRepository;
import com.capstone.licencelifecyclemanagement.services.RmaService;
import com.capstone.licencelifecyclemanagement.services.RmaService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RMAServiceTest {

    @InjectMocks
    private RmaService rmaService;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DevicePurchaseRepository devicePurchaseRepository;

    @Mock
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private RMARepository rmaRepository;

    @Test
    void testMoveSoftwareToRma() {

        int softwareId = 1;
        String reason = "Test Reason";
        Software software = new Software();
        software.setId(softwareId);

        when(softwareRepository.findById(softwareId)).thenReturn(Optional.of(software));

        rmaService.moveSoftwareToRma(softwareId, reason);

        verify(rmaRepository, times(1)).save(any(RMA.class));
    }

    @Test
    void testMoveDeviceToRma() {
        // Arrange
        int deviceId = 1;
        String reason = "Test Reason";
        Device device = new Device();
        device.setId(deviceId);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        // Act
        rmaService.moveDeviceToRma(deviceId, reason);

        // Assert
        verify(rmaRepository, times(1)).save(any(RMA.class));
    }

    @Test
    void testPutBackDeviceFromRma() {
        // Arrange
        int rmaId = 1;
        Device device = new Device();
        device.setId(1);
        device.setExpiryDate(LocalDate.now());

        RMA rma = new RMA();
        rma.setId(rmaId);
        rma.setDevice(device);
        rma.setRequestDate(LocalDate.now().minusMonths(1));

        when(rmaRepository.findById(rmaId)).thenReturn(Optional.of(rma));

        // Act
        rmaService.putBackDeviceFromRma(rmaId);

        // Assert
        verify(devicePurchaseRepository, times(1)).save(any(DevicePurchase.class));
        verify(rmaRepository, times(1)).delete(rma);
    }

    @Test
    void testPutBackSoftwareFromRma() {
        // Arrange
        int rmaId = 1;
        Software software = new Software();
        software.setId(1);
        software.setExpiryDate(LocalDate.now());

        RMA rma = new RMA();
        rma.setId(rmaId);
        rma.setSoftware(software);
        rma.setRequestDate(LocalDate.now().minusMonths(1));

        when(rmaRepository.findById(rmaId)).thenReturn(Optional.of(rma));

        // Act
        rmaService.putBackSoftwareFromRma(rmaId);

        // Assert
        verify(softwarePurchaseRepository, times(1)).save(any(SoftwarePurchase.class));
        verify(rmaRepository, times(1)).delete(rma);
    }

    @Test
    void testGetRma() {
        // Arrange
        List<RMA> expected = new ArrayList<>();
        when(rmaRepository.findAll()).thenReturn(expected);

        // Act
        List<RMA> actual = rmaService.getRma();

        // Assert
        assertEquals(expected, actual);
    }

}
