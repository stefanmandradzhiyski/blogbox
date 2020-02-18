package com.snmi.client;

import com.snmi.dto.AuthenticationDTO;
import com.snmi.dto.JwtTokenResponse;
import com.snmi.enums.ResponseType;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxRESTTemplate;
import com.snmi.util.BlogBoxSecurityGlobalConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * Auth client which is used by outside world to send request to the internal auth resource and get token
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class AuthServiceClient {

    public AuthServiceClient() {

    }

    public ResponseEntity<?> login(String token, AuthenticationDTO authenticationDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BlogBoxGlobalConstant.AUTH_BASE_URL + "/login",
                HttpMethod.POST,
                authenticationDTO,
                null,
                null,
                JwtTokenResponse.class,
                ResponseType.UNIT
        );
    }

    public String generateAnonymousToken() {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(BlogBoxSecurityGlobalConstant.JWT_SECRET.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam(BlogBoxSecurityGlobalConstant.TOKEN_TYPE_HEADER, BlogBoxSecurityGlobalConstant.TOKEN_TYPE)
                .setIssuer(BlogBoxSecurityGlobalConstant.TOKEN_ISSUER)
                .setAudience(BlogBoxSecurityGlobalConstant.TOKEN_AUDIENCE)
                .claim(BlogBoxSecurityGlobalConstant.USERNAME, "blogboxanonymoususername")
                .claim(BlogBoxSecurityGlobalConstant.PASSWORD, "1q2w3e")
                .claim(BlogBoxSecurityGlobalConstant.ROLES, new ArrayList<>())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .compact();
    }

}
