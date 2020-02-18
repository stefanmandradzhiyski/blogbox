package com.snmi.service;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserManageRoleDTO;
import com.snmi.dto.UserSearchDTO;
import com.snmi.enums.DetailLevel;
import com.snmi.enums.UserFilter;
import com.snmi.enums.Order;
import com.snmi.enums.Status;
import com.snmi.enums.ManageRoleAction;
import com.snmi.exception.BlogBoxBadRequestException;
import com.snmi.exception.BlogBoxNotFoundException;
import com.snmi.model.News;
import com.snmi.model.Role;
import com.snmi.model.User;
import com.snmi.repository.FavouriteRepository;
import com.snmi.repository.NewsRepository;
import com.snmi.repository.RoleRepository;
import com.snmi.repository.UserRepository;
import com.snmi.repository.UserRepositoryImpl;
import com.snmi.util.BlogFilter;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogMapper;
import com.snmi.util.BlogBoxSecurityUtil;
import com.snmi.util.BlogValidator;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service implementation
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final FavouriteRepository favouriteRepository;
    private final NewsRepository newsRepository;
    private final RoleRepository roleRepository;
    private final BlogMapper blogMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        UserRepositoryImpl userRepositoryImpl,
        FavouriteRepository favouriteRepository,
        NewsRepository newsRepository,
        RoleRepository roleRepository,
        BlogMapper blogMapper,
        BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRepositoryImpl = userRepositoryImpl;
        this.favouriteRepository = favouriteRepository;
        this.newsRepository = newsRepository;
        this.roleRepository = roleRepository;
        this.blogMapper = blogMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Register new user on the system
     * @param userDTO take the user data transfer object
     * @return the registered user
     */
    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        validateUniqueUsername(userDTO.getUsername());
        BlogValidator.validatePassword(userDTO.getPassword());
        User newUser = blogMapper.toUser(userDTO);
        newUser.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        newUser.setRoles(new HashSet<>(roleRepository.findAllById(Collections.singletonList(1L))));

        User savedUser = userRepository.save(newUser);

        return blogMapper.toUserDTO(savedUser, DetailLevel.SHORT);
    }

    /**
     * Get specific user by id
     * @param userId take the user id
     * @param display take the display flag
     * @return the found user
     */
    @Override
    public UserDTO getUserById(Long userId, boolean display) {
        User foundUser = findUserById(userId, display);
        DetailLevel detailLevel = BlogBoxSecurityUtil.getDetailLevelByAuthority();

        return blogMapper.toUserDTO(foundUser, detailLevel);
    }

    /**
     * Get specific user by username
     * @param username take the username
     * @param display take the display flag
     * @return the found user
     */
    @Override
    public UserDTO getUserByUsername(String username, boolean display) {
        User foundUser = findUserByUsername(username, display);
        DetailLevel detailLevel = BlogBoxSecurityUtil.getDetailLevelByAuthority();

        return blogMapper.toUserDTO(foundUser, detailLevel);
    }

    /**
     * Get user favourites
     * User can see only his favourites list
     * @param userId take the user id
     * @param display take the display flag
     * @return the user's favourites list
     */
    @Transactional
    @Override
    public List<NewsDTO> getUserFavourites(Long userId, boolean display) {
        User user = findUserById(userId, display);
        BlogBoxSecurityUtil.controlYourData(user.getUsername());

        return favouriteRepository.findAllByUserId(userId)
                .stream()
                .map(favourite -> blogMapper.toNewsDTO(favourite.getNews(), DetailLevel.SHORT, false))
                .collect(Collectors.toList());
    }

    /**
     * Get all user news
     * @param userId take the user id
     * @param display take the display flag
     * @return the user's news list
     */
    @Transactional
    @Override
    public List<NewsDTO> getUserNews(Long userId, boolean display) {
        findUserById(userId, display);

        List<News> userNews = newsRepository.findAllByUserIdAndDisplay(userId, display);
        if (!userNews.isEmpty()) {
            if (!BlogBoxSecurityUtil.isYours(userNews.get(0).getUser().getUsername()) &&
                    !BlogBoxSecurityUtil.haveYouGotAuthority(BlogBoxGlobalConstant.ROLE_ADMIN)) {
                userNews = newsRepository.findAllByUserIdAndDisplayAndStatus(userId, display, Status.APPROVED);
            }
        }

        return userNews
                .stream()
                .map(news -> blogMapper.toNewsDTO(news, DetailLevel.SHORT, false))
                .collect(Collectors.toList());
    }

    /**
     * Get all system users
     * @param display take the display flag
     * @return all system users
     */
    @Override
    public List<UserDTO> getAllUsers(boolean display) {
        return userRepository.findAllByDisplay(display)
                .stream()
                .map(user -> blogMapper.toUserDTO(user, DetailLevel.FULL))
                .collect(Collectors.toList());
    }

    /**
     * Search for users by specific criteria
     * @param userSearchDTO take the users search request data transfer object
     * @param display take the display flag
     * @return the filtered users list
     */
    @Transactional
    @Override
    public List<UserDTO> searchUsers(UserSearchDTO userSearchDTO, boolean display) {
        UserFilter userFilter = userSearchDTO.getUserFilter();
        Order order = userSearchDTO.getOrder();
        List<User> users = userRepositoryImpl.searchUsers(userSearchDTO, display);

        return BlogFilter.filter(users, userFilter, order)
            .stream()
            .map(user -> blogMapper.toUserDTO(user, DetailLevel.SHORT))
            .collect(Collectors.toList());
    }

    /**
     * Update specific user
     * User can update only their profile
     * @param userDTO take the user data transfer object
     * @param display take the display flag
     * @return the updated user
     */
    @Transactional
    @Override
    public UserDTO update(UserDTO userDTO, boolean display) {
        User existingUser = findUserById(userDTO.getId(), display);
        BlogBoxSecurityUtil.controlYourData(existingUser.getUsername());
        User updatedUser = updateUser(existingUser, userDTO);

        userRepository.save(updatedUser);

        return getUserById(updatedUser.getId(), display);
    }

    /**
     * Delete specific user
     * @param userId take the user id
     * @return the user before the delete
     */
    @Override
    public UserDTO deleteUserById(Long userId) {
        User foundUser = findUserById(userId, true);
        foundUser.setDisplay(false);
        foundUser.setUpdatedAt(new Date());

        userRepository.save(foundUser);

        return getUserById(foundUser.getId(), false);
    }

    /**
     * Manage user roles
     * User can be promoted or demoted
     * @param userManageRoleDTO take the manage role data transfer object
     * @param display take the display flag
     * @return the updated user
     */
    @Transactional
    @Override
    public UserDTO manageUserRole(UserManageRoleDTO userManageRoleDTO, boolean display) {
        User user = findUserById(userManageRoleDTO.getUserId(), display);
        Role role = findRoleById(userManageRoleDTO.getRoleId());

        if (userManageRoleDTO.getManageRoleAction() == ManageRoleAction.PROMOTE) {
            user.getRoles().add(role);
        } else if (userManageRoleDTO.getManageRoleAction() == ManageRoleAction.DEMOTE) {
            user.getRoles().remove(role);
        }
        user.setUpdatedAt(new Date());

        return getUserById(userManageRoleDTO.getUserId(), display);
    }

    /**
     * Restore deleted user
     * @param userId take the user id
     * @return the restored user
     */
    @Override
    public UserDTO restoreUser(Long userId) {
        User user = findUserById(userId, false);
        user.setDisplay(true);
        user.setUpdatedAt(new Date());

        userRepository.save(user);

        return getUserById(user.getId(), true);
    }

    private User findUserById(Long userId, boolean display) {
        BlogValidator.validateId(userId, User.class);

        return userRepository.findByIdAndDisplay(userId, display)
                .orElseThrow(() -> new BlogBoxNotFoundException(User.class, userId));
    }

    private Role findRoleById(Long roleId) {
        BlogValidator.validateId(roleId, Role.class);

        return roleRepository.findById(roleId)
            .orElseThrow(() -> new BlogBoxNotFoundException(Role.class, roleId));
    }

    private User findUserByUsername(String username, boolean display) {
        BlogValidator.validateStringField(username, BlogBoxGlobalConstant.USERNAME, 3, 25);

        return userRepository.findByUsernameAndDisplay(username, display)
            .orElseThrow(() -> new BlogBoxNotFoundException(User.class, BlogBoxGlobalConstant.USERNAME, username));
    }

    private void validateUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BlogBoxBadRequestException(String.format(BlogBoxGlobalConstant.UNIQUE_USERNAME_MESSAGE, username));
        }
    }

    private User updateUser(User existingUser, UserDTO userDTO) {
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setUpdatedAt(new Date());

        return existingUser;
    }
}
