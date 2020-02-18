package com.snmi.util;

/**
 * BlogBoxGlobalConstant holds all constants in the system
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class BlogBoxGlobalConstant {

    //URL
    public final static String BLOG_BASE_URL = "http://localhost:8080";
    public final static String AUTH_BASE_URL = "http://localhost:8888/api/v1/auth";
    public final static String USERS_BASE_URL = "/api/v1/users";
    public final static String NEWS_BASE_URL = "/api/v1/news";
    public final static String COMMENTS_BASE_URL = "/api/v1/comments";

    //FIELDS
    public static final String FIRST_NAME = "First name";
    public static final String LAST_NAME = "Last name";
    public static final String USERNAME = "Username";
    public static final String NEWS_NAME = "Name";
    public static final String PASSWORD = "Password";
    public static final String NEWS_SHORT_DESCRIPTION = "Short description";
    public static final String NEWS_MAIN_INFORMATION = "Main information";
    public static final String COMMENT = "Comment";

    //ROLES
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    //MESSAGE
    public static final String UNIQUE_USERNAME_MESSAGE
            = "Username '%s' is already busy. Choose unique username.";
    public static final String UTIL_CLASS_EXCEPTION_MESSAGE = "Can't create an instance of static util class";
    public static final String FORBIDDEN_ACCESS = "You don't have authority to execute that action";
    public static final String DISABLED_ACCOUNT = "Your account is disabled. Contact admin!";

    //PATH VARIABLES
    public static final String PATH_USER_ID = "userId";
    public static final String PATH_NEWS_ID = "newsId";
    public static final String PATH_USERNAME = "username";
    public static final String PATH_COMMENT_ID = "commentId";
    public static final String PATH_DISPLAY = "display";


    private BlogBoxGlobalConstant() {
        throw new IllegalArgumentException(BlogBoxGlobalConstant.UTIL_CLASS_EXCEPTION_MESSAGE);
    }

}
