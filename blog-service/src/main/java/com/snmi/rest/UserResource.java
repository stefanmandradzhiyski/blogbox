package com.snmi.rest;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserManageRoleDTO;
import com.snmi.dto.UserSearchDTO;
import com.snmi.service.UserService;
import com.snmi.service.UserServiceImpl;
import com.snmi.util.BlogBoxGlobalConstant;
import io.swagger.annotations.Api;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Api(value = "User API")
@RestController
@RequestMapping("api/v1/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been registered successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid password",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @ApiOperation(value = "Get specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
        @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId,
        @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.getUserById(userId, display));
    }

    @ApiOperation(value = "Get specific user by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid username",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/un/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(
            @PathVariable(BlogBoxGlobalConstant.PATH_USERNAME) String username,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.getUserByUsername(username, display));
    }

    @ApiOperation(value = "Get user's favourites")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User favourites list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "You can't retrieve other user favourites list",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/favourites/{userId}")
    public ResponseEntity<List<NewsDTO>> getUserFavourites(
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.getUserFavourites(userId, display));
    }

    @ApiOperation(value = "Get user's news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User news list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/news/{userId}")
    public ResponseEntity<List<NewsDTO>> getUserNews(
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.getUserNews(userId, display));
    }

    @ApiOperation(value = "Get all system users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "System users list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display) {
        return ResponseEntity.ok(userService.getAllUsers(display));
    }


    @ApiOperation(value = "Search users by criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @Valid @RequestBody UserSearchDTO userSearchDTO,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.searchUsers(userSearchDTO, display));
    }

    @ApiOperation(value = "Update specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been updated successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "You can't manipulate other user",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("")
    public ResponseEntity<UserDTO> updateUser(
            @Valid @RequestBody UserDTO userDTO,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.update(userDTO, display));
    }

    @ApiOperation(value = "Delete specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been deleted successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> deleteUserById(@PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId) {
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }

    @ApiOperation(value = "Change specific user's roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been updated successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user or role id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User or role doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/manage-user-roles")
    public ResponseEntity<UserDTO> manageUserRole(
            @Valid @RequestBody UserManageRoleDTO userManageRoleDTO,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(userService.manageUserRole(userManageRoleDTO, display));
    }

    @ApiOperation(value = "Restore specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User has been restored successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/restore/{userId}")
    public ResponseEntity<UserDTO> restoreUser(@PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId) {
        return ResponseEntity.ok(userService.restoreUser(userId));
    }

}
