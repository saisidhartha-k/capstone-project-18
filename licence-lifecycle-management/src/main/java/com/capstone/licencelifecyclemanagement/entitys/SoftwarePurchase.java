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
public class SoftwarePurchase {
    
    public SoftwarePurchase(SoftwarePurchaseId id) {
        this.softwarePurchaseId = id;
    }

    @EmbeddedId
    private SoftwarePurchaseId softwarePurchaseId;
    private LocalDate purchaseDate = LocalDate.now();
}
