package com.capstone.licencelifecyclemanagement.entitys;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SoftwarePurchaseId  implements Serializable {
    
    public SoftwarePurchaseId(Software software) {
        this.software = software;
    }

    private String LicenseNumber;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Software software;
}
