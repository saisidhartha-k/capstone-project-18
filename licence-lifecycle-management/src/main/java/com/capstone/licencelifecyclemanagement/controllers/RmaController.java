package com.capstone.licencelifecyclemanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.services.RmaService;

@CrossOrigin()
@RestController
@RequestMapping("/software-rma")
public class RmaController {
    @Autowired
    private RmaService rmaService;

    @PostMapping("/move/{softwareId}")
    public ResponseEntity<Void> moveSoftwareToRma(@PathVariable int softwareId, @RequestBody RMA rma) {
        try {
            rmaService.moveToRma(softwareId, rma);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/putback/{id}")
    public void moveSoftwareToRma(@PathVariable int id) {

        rmaService.putBackFromRma(id);
    }
}