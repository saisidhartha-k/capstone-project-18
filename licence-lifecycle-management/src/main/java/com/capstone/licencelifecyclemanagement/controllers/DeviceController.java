package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.services.DeviceService;

import jakarta.transaction.Transactional;

@CrossOrigin()
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping("/adddevice")
    @Transactional
    public Device addDevice(@RequestBody Device device)
    {
        return deviceService.addDevice(device);
    }

    @GetMapping("/get")
    public List<Device> getSoftwares()
    {
        return deviceService.getDevices();
    }

    @PostMapping("/renew/{id}")
    public String renewDevice(@PathVariable("id") int id, @RequestBody DeviceDto dto) {
        return deviceService.renewDevice(id, dto);
    }

    @GetMapping("/getExpired")
    public List<Device> getExpired()
    {
        return deviceService.expierdDevices();
    }

    @GetMapping("/getAboutExpired")
    public List<Device> getAboutExpired()
    {
        return deviceService.devicesAboutToExpire();
    }
    
    @GetMapping("/aboutToExpireCount")
    public int getDevicesAboutToExpireCount() {
        return deviceService.devicesAboutToExpireCount();
    }

    @GetMapping("/notExpiredCount")
    public int getNotExpiredDeviceCount() {
        return deviceService.devicesNotExpiredCount();
    }

    @GetMapping("/expiredCount")
    public int getExpiredDevicesCount() {
        return deviceService.expiredDevicesCount();
    }

    @GetMapping("/percentageAboutToExpire")
    public int getPercentageOfDevicesAboutToExpire() {
        return deviceService.percentageOfDevicesAboutToExpire();
    }

    @GetMapping("/percentageNotExpired")
    public int getPercentageOfNotExpiredDevices() {
        return deviceService.percentageOfNotExpiredDevices();
    }

    @GetMapping("/percentageExpired")
    public int getPercentageOfExpiredDevices() {
        return deviceService.percentageOfExpiredDevices();
    }

    @DeleteMapping("/decomissionDevice/{id}")
    public void decomissionDevice(@PathVariable int id)
    {
        deviceService.decomissionDevice(id);
    }

    @PostMapping("/assetcheck")
    public List<String> assetCheck()
    {
        return deviceService.assetCheck();
    }

    @PostMapping("/sendmail")
    public boolean sendmail()
    {
        return deviceService.sendServiceTerminationEmail();
    }
}
