package com.snmi.rest;

import com.snmi.client.AuthServiceClient;
import com.snmi.client.UserServiceClient;
import com.snmi.dto.UserDTO;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Api(value = "Member user API")
@RestController
@RequestMapping("api/v1/users")
public class UserResource {

    private final UserServiceClient userServiceClient;
    private final AuthServiceClient authServiceClient;

    public UserResource(UserServiceClient userServiceClient, AuthServiceClient authServiceClient) {
        this.userServiceClient = userServiceClient;
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        String token = authServiceClient.generateAnonymousToken();
        return userServiceClient.registerUser(token, userDTO);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return userServiceClient.getUserById(token, userId, true);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/un/{username}")
    public ResponseEntity<?> getUserByUsername(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USERNAME) String username
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return userServiceClient.getUserByUsername(token, username, true);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/favourites/{userId}")
    public ResponseEntity<?> getUserFavourites(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return userServiceClient.getUserFavourites(token, userId, true);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/news/{userId}")
    public ResponseEntity<?> getUserNews(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return userServiceClient.getUserNews(token, userId, true);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @Valid @RequestBody UserSearchDTO userSearchDTO
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return userServiceClient.searchUsers(token, userSearchDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("")
    public ResponseEntity<?> updateUser(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody UserDTO userDTO
    ) {
        return userServiceClient.updateUser(token, userDTO, true);
    }

}
