package com.capstone.licencelifecyclemanagement.entitys;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DevicePurchaseId implements Serializable {

    public DevicePurchaseId(Device device) {
        this.device = device;
    }

    private String LicenseNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    private Device device;
}
