package com.capstone.licencelifecyclemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {
    private String toEmail;
    private String subject;
    private String body;
}