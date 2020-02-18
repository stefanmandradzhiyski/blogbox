package com.snmi.service;

import com.snmi.dto.JwtTokenResponse;
import com.snmi.exception.BlogBoxBadRequestException;
import com.snmi.exception.BlogBoxNotFoundException;
import com.snmi.model.JwtUser;
import com.snmi.repository.JwtUserRepository;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxSecurityGlobalConstant;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Authentication Service
 */
@Service
public class AuthenticationService {

    private final JwtUserRepository jwtUserRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            JwtUserRepository jwtUserRepository,
            JwtTokenService jwtTokenService,
            PasswordEncoder passwordEncoder
    ) {
        this.jwtUserRepository = jwtUserRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Generate JWT token from the found JwtUser data
     * @param username take the username
     * @param password take the password
     * @return the generated token
     */
    public JwtTokenResponse generateJWTToken(String username, String password) {
        JwtUser foundJwtUser = findJwtUserByUsername(username);
        if (!foundJwtUser.isDisplay()) {
            throw new BlogBoxBadRequestException(BlogBoxGlobalConstant.DISABLED_ACCOUNT);
        }

        return jwtUserRepository.findByUsernameAndDisplay(username, true)
                .filter(jwtUser ->  passwordEncoder.matches(password, jwtUser.getPassword()))
                .map(jwtUser -> new JwtTokenResponse(jwtTokenService.generateToken(jwtUser)))
                .orElseThrow(() ->  new EntityNotFoundException(BlogBoxSecurityGlobalConstant.WRONG_USER_CREDENTIALS));
    }

    private JwtUser findJwtUserByUsername(String username) {
        return jwtUserRepository.findByUsername(username)
                .orElseThrow(() -> new BlogBoxNotFoundException(JwtUser.class, BlogBoxGlobalConstant.USERNAME, username));
    }
}
