package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;
import com.capstone.licencelifecyclemanagement.services.DecommissionedItemService;

@CrossOrigin()
@RestController
@RequestMapping("/decomssioneditems")
public class DecomissionedItemController {
    @Autowired
    private DecommissionedItemService decommissionedItemService;

    @GetMapping("/get")
    public List<DecommissionedItem> getDecomissionedItems()
    {
        return decommissionedItemService.getDecomissionedItems();
    }
}
