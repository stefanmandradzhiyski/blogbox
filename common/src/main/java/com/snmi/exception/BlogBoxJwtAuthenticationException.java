package com.snmi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BlogBoxJwtAuthenticationException can be used when you want to throw 401 status code
 * when attempting to authenticate specific user in the system
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class BlogBoxJwtAuthenticationException extends AuthenticationException {

    public BlogBoxJwtAuthenticationException(String message) {
        super(message);
    }

}
