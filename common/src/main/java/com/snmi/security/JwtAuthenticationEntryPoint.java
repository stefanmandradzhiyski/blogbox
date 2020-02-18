package com.snmi.security;

import com.snmi.util.BlogBoxSecurityGlobalConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * JwtAuthenticationEntryPoint is used to hold the generated token and return it as response to the client
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, BlogBoxSecurityGlobalConstant.UNAUTHORIZED);
    }
}
