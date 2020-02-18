package com.snmi.security;

import com.snmi.exception.BlogBoxJwtAuthenticationException;
import com.snmi.util.BlogBoxSecurityGlobalConstant;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * JwtAuthenticationProvider is used to authenticate a user
 * Validates the token
 * Uses the JwtTokenParserService to get values from the already existing token
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final JwtTokenParserService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtTokenParserService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            String token = (String) authentication.getCredentials();
            String username = jwtService.getStringValueFromToken(token, BlogBoxSecurityGlobalConstant.USERNAME);
            String password = jwtService.getStringValueFromToken(token, BlogBoxSecurityGlobalConstant.PASSWORD);
            List<SimpleGrantedAuthority> roles = jwtService.getRolesFromToken(token);

            return jwtService.validateToken(token)
                    .map(bool -> new UsernamePasswordAuthenticationToken(username, password, roles))
                    .orElseThrow(() -> new BlogBoxJwtAuthenticationException(BlogBoxSecurityGlobalConstant.JWT_VALIDATION_FAILED));

        } catch (JwtException ex) {
            log.error(String.format("%s %s", BlogBoxSecurityGlobalConstant.INVALID_JWT_TOKEN, ex.getMessage()));
            throw new BlogBoxJwtAuthenticationException(BlogBoxSecurityGlobalConstant.INVALID_JWT_TOKEN);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
