package com.snmi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BlogBoxForbiddenException can be used when you want to throw 403 status code
 * when attempting to manipulate prohibited data
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class BlogBoxForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -6144917362290970369L;

    public BlogBoxForbiddenException(String message) {
        super(message);
    }

}
