package com.snmi.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Authentication data transfer object
 * Used by the login endpoint
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class AuthenticationDTO {

    @ApiModelProperty(name = "User username", example = "snmi", access = "WRITE", required = true)
    @NotNull(message = "Username is required when you are trying to authenticate")
    private String username;

    @ApiModelProperty(name = "User password", example = "123456s", access = "WRITE", required = true)
    @NotNull(message = "Password is required when you are trying to authenticate")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
