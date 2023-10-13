package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.dto.DeviceDto;
import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.services.DeviceService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin()
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
        // softwareservice.assetCheck();
        return deviceService.getDevices();
    }

    @PostMapping("/renew/{id}")
    public String renewDevice(@PathVariable("id") int id, @RequestBody DeviceDto dto) {
        return deviceService.renewDevice(id, dto);
    }

}
