package com.snmi.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * JWT Token response data transfer object
 * Used by the AuthenticationService to return the already generated token to the outside world (client)
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class JwtTokenResponse {

    @ApiModelProperty(name = "Token", access = "READ")
    private String token;

    public JwtTokenResponse() {

    }

    public JwtTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
