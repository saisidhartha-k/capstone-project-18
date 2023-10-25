package com.capstone.licencelifecyclemanagement.entitys;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DevicePurchase {
    
    public DevicePurchase(DevicePurchaseId id) {
        this.devicePurchaseId = id;
    }

    @EmbeddedId
    private DevicePurchaseId devicePurchaseId;
    private LocalDate purchaseDate = LocalDate.now();
}
