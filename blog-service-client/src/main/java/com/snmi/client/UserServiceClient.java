package com.snmi.client;

import com.snmi.dto.NewsDTO;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxRESTTemplate;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserManageRoleDTO;
import com.snmi.dto.UserSearchDTO;
import com.snmi.enums.ResponseType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User client which is used by outside world to send request to the internal user resource
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class UserServiceClient {

    private static final String BLOG_USER_BASE_URL
            = BlogBoxGlobalConstant.BLOG_BASE_URL + BlogBoxGlobalConstant.USERS_BASE_URL;

    public UserServiceClient() {

    }

    public ResponseEntity<?> registerUser(String token, UserDTO userDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/register",
                HttpMethod.POST,
                userDTO,
                null,
                null,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> getUserById(String token, Long userId, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/",
                HttpMethod.GET,
                null,
                Long.toString(userId),
                params,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> getUserByUsername(String token, String username, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/un/",
                HttpMethod.GET,
                null,
                username,
                params,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> getUserFavourites(String token, Long userId, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/favourites/",
                HttpMethod.GET,
                null,
                Long.toString(userId),
                params,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> getUserNews(String token, Long userId, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/news/",
                HttpMethod.GET,
                null,
                Long.toString(userId),
                params,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> getAllUsers(String token, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL,
                HttpMethod.GET,
                null,
                null,
                params,
                UserDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> searchUsers(String token, UserSearchDTO userSearchDTO, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/search",
                HttpMethod.POST,
                userSearchDTO,
                null,
                params,
                UserDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> updateUser(String token, UserDTO userDTO, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL,
                HttpMethod.PUT,
                userDTO,
                null,
                params,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> deleteUserById(String token, Long userId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/",
                HttpMethod.DELETE,
                null,
                Long.toString(userId),
                null,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> manageUserRole(String token, UserManageRoleDTO userManageRoleDTO, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/manage-user-roles",
                HttpMethod.POST,
                userManageRoleDTO,
                null,
                params,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> restoreUser(String token, Long userId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_USER_BASE_URL + "/restore/",
                HttpMethod.PUT,
                null,
                Long.toString(userId),
                null,
                UserDTO.class,
                ResponseType.UNIT
        );
    }

}
