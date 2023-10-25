package com.capstone.licencelifecyclemanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;
import com.capstone.licencelifecyclemanagement.repository.DecommisionedItemRepository;

@Service
public class DecommissionedItemService {

    @Autowired
    private DecommisionedItemRepository decommisionedItemRepository;

    public List<DecommissionedItem> getDecomissionedItems()
    {
        return decommisionedItemRepository.findAll();
    }
    
}
