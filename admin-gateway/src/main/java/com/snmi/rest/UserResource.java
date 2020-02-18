package com.snmi.rest;

import com.snmi.client.UserServiceClient;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserManageRoleDTO;
import com.snmi.dto.UserSearchDTO;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxSecurityGlobalConstant;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Api(value = "Admin user API")
@RestController
@RequestMapping("api/v1/users")
public class UserResource {

    private final UserServiceClient userServiceClient;

    public UserResource(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return userServiceClient.getUserById(token, userId, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/un/{username}")
    public ResponseEntity<?> getUserByUsername(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USERNAME) String username
    ) {
        return userServiceClient.getUserByUsername(token, username, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/favourites/{userId}")
    public ResponseEntity<?> getUserFavourites(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return userServiceClient.getUserFavourites(token, userId, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/news/{userId}")
    public ResponseEntity<?> getUserNews(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return userServiceClient.getUserNews(token, userId, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllUsers(@RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token) {
        return userServiceClient.getAllUsers(token, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody UserSearchDTO userSearchDTO
    ) {
        return userServiceClient.searchUsers(token, userSearchDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public ResponseEntity<?> updateUser(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody UserDTO userDTO
    ) {
        return userServiceClient.updateUser(token, userDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/manage-user-roles")
    public ResponseEntity<?> manageUserRole(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody UserManageRoleDTO userManageRoleDTO
    ) {
        return userServiceClient.manageUserRole(token, userManageRoleDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return userServiceClient.deleteUserById(token, userId);
    }

}
