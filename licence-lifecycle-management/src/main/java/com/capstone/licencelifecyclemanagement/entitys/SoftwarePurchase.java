package com.capstone.licencelifecyclemanagement.entitys;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
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
