package com.snmi.service;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserManageRoleDTO;
import com.snmi.dto.UserSearchDTO;
import java.util.List;

/**
 * User service functionality contract
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public interface UserService {

    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUserById(Long userId, boolean display);
    UserDTO getUserByUsername(String username, boolean display);
    List<NewsDTO> getUserFavourites(Long userId, boolean display);
    List<NewsDTO> getUserNews(Long userId, boolean display);
    List<UserDTO> getAllUsers(boolean display);
    List<UserDTO> searchUsers(UserSearchDTO userSearchDTO, boolean display);
    UserDTO update(UserDTO userDTO, boolean display);
    UserDTO deleteUserById(Long userId);
    UserDTO manageUserRole(UserManageRoleDTO userManageRoleDTO, boolean display);
    UserDTO restoreUser(Long userId);

}
