package com.snmi.rest;

import com.snmi.dto.AuthenticationDTO;
import com.snmi.dto.JwtTokenResponse;
import com.snmi.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Authentication API")
@RestController
@RequestMapping("api/v1/auth")
public class AuthResource {

    private final AuthenticationService authenticationService;

    public AuthResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been logged successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Disabled account/Wrong credentials",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User not found",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok(authenticationService.generateJWTToken(
                authenticationDTO.getUsername(),
                authenticationDTO.getPassword())
        );
    }

}
