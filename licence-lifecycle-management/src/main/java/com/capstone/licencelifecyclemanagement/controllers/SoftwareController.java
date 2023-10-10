package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

@RestController
@RequestMapping("/software")
public class SoftwareController {
    
    @Autowired
    private SoftwareService softwareservice;

    @GetMapping("/software")
    public void assetCheck()
    {
         softwareservice.SendEmail(10);
    }

    @PostMapping("/addsoftware")
    public Software addSoftware(@RequestBody Software software)
    {
        return softwareservice.addSoftware(software);
    }

    @GetMapping("/get")
    public List<Software> getSoftwares()
    {
        return softwareservice.getSoftwares();
    }

}
