package com.snmi.util;

import com.snmi.exception.BlogBoxForbiddenException;
import com.snmi.enums.DetailLevel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.snmi.model.AuthenticatedUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * BlogBoxSecurityUtil can be used to check several things connected to the authenticated user
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class BlogBoxSecurityUtil {

    private BlogBoxSecurityUtil() {
        throw new IllegalArgumentException(BlogBoxGlobalConstant.UTIL_CLASS_EXCEPTION_MESSAGE);
    }

    /**
     * Check if the data you changing is yours
     * If it is not, the forbidden exception will be thrown
     * @param username take the creator username of specific entity
     */
    public static void controlYourData(String username) {
        if (!haveYouGotAuthority(BlogBoxGlobalConstant.ROLE_ADMIN)) {
            AuthenticatedUser authenticatedUser = getLoggedInUser();
            if (authenticatedUser == null || !authenticatedUser.getUsername().equalsIgnoreCase(username)) {
                throw new BlogBoxForbiddenException(BlogBoxGlobalConstant.FORBIDDEN_ACCESS);
            }
        }
    }

    /**
     * Get DetailLevel by authenticated user authority
     * @return the DetailLevel
     */
    public static DetailLevel getDetailLevelByAuthority() {
        Set<String> authorities = getUserAuthorities();
        if (authorities.contains(BlogBoxGlobalConstant.ROLE_ADMIN)) {
            return DetailLevel.FULL;
        } else if (authorities.contains(BlogBoxGlobalConstant.ROLE_USER)) {
            return DetailLevel.HALF;
        } else {
            return DetailLevel.SHORT;
        }
    }

    /**
     * Check if specific record is created by you
     * @param username take the creator username of specific entity
     * @return true if it is yours
     */
    public static boolean isYours(String username) {
        AuthenticatedUser authenticatedUser = getLoggedInUser();
        return authenticatedUser != null && authenticatedUser.getUsername().equalsIgnoreCase(username.toLowerCase());
    }

    /**
     * Check if you got authority to execute some specific task
     * @param authority take the desired authority
     * @return true if the authenticated user has got that authority
     */
    public static boolean haveYouGotAuthority(String authority) {
        Set<String> authorities = getUserAuthorities();
        return authorities.contains(authority);
    }

    public static String getLoggedInUserUsername() {
        AuthenticatedUser authenticatedUser = getLoggedInUser();
        if (authenticatedUser == null) {
            return null;
        }

        return authenticatedUser.getUsername();
    }

    private static Set<String> getUserAuthorities() {
        AuthenticatedUser authenticatedUser = getLoggedInUser();
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (authenticatedUser != null) {
            authorities.addAll(authenticatedUser.getAuthorities());
        }

        return authorities
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());
    }

    private static AuthenticatedUser getLoggedInUser() {
        if (isAuthenticatedAsAnonymous()) {
            return null;
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        authenticatedUser.setAuthorities(
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        );
        return authenticatedUser;
    }

    private static boolean isAuthenticatedAsAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }
}
