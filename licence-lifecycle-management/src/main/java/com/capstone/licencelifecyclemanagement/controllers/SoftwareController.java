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

import com.capstone.licencelifecyclemanagement.dto.SoftwareDto;
import com.capstone.licencelifecyclemanagement.entitys.Software;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin()
@RequestMapping("/software")
public class SoftwareController {
    
    @Autowired
    private SoftwareService softwareservice;

    @PostMapping("/addsoftware")
    @Transactional
    public Software addSoftware(@RequestBody Software software)
    {
        System.out.println(software);
        return softwareservice.addSoftware(software);
    }

    @GetMapping("/getNotExpired")
     public List<Software> getNotExpired()
    {
        return softwareservice.notExpList();
    } 

    @GetMapping("/getExpired")
      public List<Software> getExpired()
    {
        return softwareservice.expiredSoftwares();
    } 

     @GetMapping("/getAboutExpired")
      public List<Software> getAboutExpired()
    {
        return softwareservice.aboutToExpire();
    } 

    
    @GetMapping("/get")
    public List<Software> getSoftwares() {
       return softwareservice.getSoftwares();
       
    }

    @GetMapping("/getExpiredCount")
    public int getExpiredCount()
    {
        return softwareservice.expiredSoftwaresCount();
    }

    @GetMapping("/getNotExpiredCount")
    public int getNotExpiredCount()
    {
        return softwareservice.notExpListCount();
    }

    @GetMapping("/getAboutExpiredCount")
    public int getAboutExpiredCount()
    {
        return softwareservice.aboutToExpireCount();
    }

    @PostMapping("/renew/{id}")
    public String renewSoftware(@PathVariable("id") int id, @RequestBody SoftwareDto dto) {
        System.out.println(dto);
        return softwareservice.renewSoftware(id, dto);
    }

    
    @GetMapping("/percentageAboutToExpire")
    public int getPercentageOfSoftwareAboutToExpire() {
        return softwareservice.percentageOfSoftwareAboutToExpire();
    }

    @GetMapping("/percentageNotExpired")
    public int getPercentageOfNotExpiredSoftware() {
        return softwareservice.percentageOfNotExpiredSoftware();
    }

    @GetMapping("/percentageExpired")
    public int getPercentageOfExpiredSoftware() {
        return softwareservice.percentageOfExpiredSoftware();
    }

  @DeleteMapping("/decomission/{id}")
    public void deleteSoftware(@PathVariable int id) {
        softwareservice.decomissionSoftware(id);
    }
   
    @PostMapping("/assetcheck")
    public List<String> assetCheck()
    {
        return softwareservice.assetCheck();
    }

}
