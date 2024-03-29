package com.capstone.licencelifecyclemanagement.controllers;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.dto.JwtResponse;
import com.capstone.licencelifecyclemanagement.dto.LoginBody;
import com.capstone.licencelifecyclemanagement.services.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> token(@RequestBody LoginBody loginBody) {
        Instant now = Instant.now();
        long expiry = 3600L;
        var username = loginBody.getUsername();
        var password = loginBody.getPassword();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        JwtResponse response = new JwtResponse(
                userService.findByName(username).get().getId(),
                jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
                authentication.getName());

        return ResponseEntity.ok().body(response);
    }

}