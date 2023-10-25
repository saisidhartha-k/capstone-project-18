package com.capstone.licencelifecyclemanagement.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private int id;
    private String token;
    private String username;

    public JwtResponse(int i,String accessToken,String username) {
        this.id = i;
        this.token = accessToken;
        this.username = username;
      }
}