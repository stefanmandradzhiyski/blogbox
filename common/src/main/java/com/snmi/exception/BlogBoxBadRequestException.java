package com.snmi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BlogBoxBadRequestException can be used when you want to throw 400 status code
 * on some definite condition
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlogBoxBadRequestException extends RuntimeException {

    private static final long serialVersionUID = -6144917362290970369L;

    public BlogBoxBadRequestException(String message) {
        super(message);
    }

}
