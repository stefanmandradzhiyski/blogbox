package com.snmi.util;

import com.snmi.exception.BlogBoxBadRequestException;
import com.snmi.model.base.BlogBoxBaseEntity;

/**
 * BlogValidator is a util class
 * Can be used to validate id, string or password
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class BlogValidator {

    private static final String NOT_NULL_ID = "The %s id can't be null";
    private static final String NOT_NULL_FIELD = "%s can't be null";
    private static final String INVALID_STRING_FIELD = "%s length must be between %d and %d";
    private static final String INVALID_PASSWORD = "Password must contains at least one %s";
    private static final String NUMBER = "number";
    private static final String LETTER = "letter";

    private BlogValidator() {
        throw new IllegalArgumentException(BlogBoxGlobalConstant.UTIL_CLASS_EXCEPTION_MESSAGE);
    }

    public static Long validateId(Long id, Class<? extends BlogBoxBaseEntity> entity) {
        if (id == null) {
            throw new BlogBoxBadRequestException(String.format(NOT_NULL_ID, entity.getSimpleName()));
        }

        return id;
    }

    public static void validateStringField(String stringField, String stringFieldName, int min, int max) {
        if (BlogBoxStringUtil.isEmpty(stringField)) {
            throw new BlogBoxBadRequestException(String.format(NOT_NULL_FIELD, stringFieldName));
        }
        if (stringField.length() < min || stringField.length() > max) {
            throw new BlogBoxBadRequestException(String.format(INVALID_STRING_FIELD, stringFieldName, min, max));
        }
    }

    public static void validatePassword(String password) {
        if (BlogBoxStringUtil.isEmpty(password)) {
            throw new BlogBoxBadRequestException(String.format(NOT_NULL_FIELD, BlogBoxGlobalConstant.PASSWORD));
        }

        if (password.length() < 6 || password.length() > 20) {
            throw new BlogBoxBadRequestException(String.format(INVALID_STRING_FIELD, BlogBoxGlobalConstant.PASSWORD, 6, 20));
        }

        if (!password.matches(".*\\d.*")) {
            throw new BlogBoxBadRequestException(String.format(INVALID_PASSWORD, NUMBER));
        }

        if (!password.matches(".*[a-zA-Z]+.*")) {
            throw new BlogBoxBadRequestException(String.format(INVALID_PASSWORD, LETTER));
        }
    }

}
