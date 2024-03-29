package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.RMA;
import com.capstone.licencelifecyclemanagement.services.RmaService;

@CrossOrigin()
@RestController
@RequestMapping("/RMA")
public class RmaController {
    @Autowired
    private RmaService rmaService;

    @PostMapping("/moveSoftware/{softwareId}")
    public ResponseEntity<Void> moveSoftwareToRma(@PathVariable int softwareId, @RequestBody RMA rma) {

        rmaService.moveSoftwareToRma(softwareId, rma.getReason());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/moveDevice/{deviceId}")
    public ResponseEntity<Void> moveDeviceToRma(@PathVariable int deviceId, @RequestBody RMA rma) {

        rmaService.moveDeviceToRma(deviceId, rma.getReason());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/putBackSoftware/{id}")
    public void putBackSoftware(@PathVariable int id) {
        rmaService.putBackSoftwareFromRma(id);
    }

    @PostMapping("/putBackDevice/{id}")
    public void putBackDevice(@PathVariable int id) {
        rmaService.putBackDeviceFromRma(id);
    }

    @GetMapping("/getRma")
    public List<RMA> getRma() {
        return rmaService.getRma();
    }

}