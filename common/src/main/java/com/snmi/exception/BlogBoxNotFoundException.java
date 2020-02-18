package com.snmi.exception;

import com.snmi.model.base.BlogBoxBaseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BlogBoxNotFoundException can be used when you want to throw 404 status code
 * when attempting to retrieve not existing data from the server
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BlogBoxNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -992963876701456114L;

    private static final String ID_MESSAGE = "%s with id=%s isn't found";
    private static final String STRING_MESSAGE = "%s with %s '%s' isn't found";

    public BlogBoxNotFoundException(Class<? extends BlogBoxBaseEntity> baseEntity, Long id) {
        super(String.format(ID_MESSAGE, baseEntity.getSimpleName(), id));
    }

    public BlogBoxNotFoundException(Class<? extends BlogBoxBaseEntity> baseEntity, String stringType, String stringValue) {
        super(String.format(STRING_MESSAGE, baseEntity.getSimpleName(), stringType, stringValue));
    }

}
