package com.snmi.util;

/**
 * Blog box custom string util which can be used to check if specific string is null or empty
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class BlogBoxStringUtil {

    private BlogBoxStringUtil() {
        throw new IllegalArgumentException(BlogBoxGlobalConstant.UTIL_CLASS_EXCEPTION_MESSAGE);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

}
