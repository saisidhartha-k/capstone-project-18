package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

@RestController
@CrossOrigin()

@RequestMapping("/software")
public class SoftwareController {
    
    @Autowired
    private SoftwareService softwareservice;


    @PostMapping("/addsoftware")
    public Software addSoftware(@RequestBody Software software)
    {
        return softwareservice.addSoftware(software);
    }

    @GetMapping("/getNotExpired")
     public List<Software> getNotExpired()
    {
        softwareservice.assetCheck();
        return softwareservice.notExpList();
    } 

    @GetMapping("/getExpired")
      public List<Software> getExpired()
    {
        softwareservice.assetCheck();
        return softwareservice.expiredSoftwares();
    } 

     @GetMapping("/getAboutExpired")
      public List<Software> getAboutExpired()
    {
        softwareservice.assetCheck();
        return softwareservice.aboutToExpire();
    } 

    @GetMapping("/get")
    public List<Software> getSoftwares()
    {
        softwareservice.assetCheck();
        return softwareservice.getSoftwares();
    }

}
