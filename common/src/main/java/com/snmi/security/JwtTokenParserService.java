package com.snmi.security;

import com.snmi.util.BlogBoxSecurityGlobalConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JwtTokenParserService purpose is to parse existing token and retrieve specific data from it
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Component
public class JwtTokenParserService {

    @Autowired
    public JwtTokenParserService() {

    }

    public String getStringValueFromToken(String token, String stringProperty) {
        return (String) getAllClaimsFromToken(token).get(stringProperty);
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        return ((List<?>) getAllClaimsFromToken(token)
                .get(BlogBoxSecurityGlobalConstant.ROLES)).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(BlogBoxSecurityGlobalConstant.JWT_SECRET.getBytes())
                .parseClaimsJws(token.replace(BlogBoxSecurityGlobalConstant.TOKEN_PREFIX, "")).getBody();
    }

    private Boolean isTokenNotExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.after(new Date());
    }

    public Optional<Boolean> validateToken(String token) {
        return  isTokenNotExpired(token) ? Optional.of(Boolean.TRUE) : Optional.empty();
    }
}
