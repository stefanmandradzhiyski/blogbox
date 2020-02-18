package com.snmi.rest;

import com.snmi.client.AuthServiceClient;
import com.snmi.dto.AuthenticationDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Authentication API")
@RestController
@RequestMapping("api/v1/auth")
public class AuthResource {

    private final AuthServiceClient authServiceClient;

    public AuthResource(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String token = authServiceClient.generateAnonymousToken();
        return authServiceClient.login(token, authenticationDTO);
    }


}
