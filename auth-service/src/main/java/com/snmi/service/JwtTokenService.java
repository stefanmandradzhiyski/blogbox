package com.snmi.service;

import com.snmi.model.JwtUser;
import com.snmi.model.Role;
import com.snmi.util.BlogBoxSecurityGlobalConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtTokenService generate an authentication token from the JwtUser data
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Component
public class JwtTokenService {

    public JwtTokenService() {

    }

    public String generateToken(JwtUser jwtUser) {
        String username = jwtUser.getUsername();
        String password = jwtUser.getPassword();
        List<String> roles = jwtUser.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(BlogBoxSecurityGlobalConstant.JWT_SECRET.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam(BlogBoxSecurityGlobalConstant.TOKEN_TYPE_HEADER, BlogBoxSecurityGlobalConstant.TOKEN_TYPE)
                .setIssuer(BlogBoxSecurityGlobalConstant.TOKEN_ISSUER)
                .setAudience(BlogBoxSecurityGlobalConstant.TOKEN_AUDIENCE)
                .claim(BlogBoxSecurityGlobalConstant.USERNAME, username)
                .claim(BlogBoxSecurityGlobalConstant.PASSWORD, password)
                .claim(BlogBoxSecurityGlobalConstant.ROLES, roles)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .compact();
    }

}
