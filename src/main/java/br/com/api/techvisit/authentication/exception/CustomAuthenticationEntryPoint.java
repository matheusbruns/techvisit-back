package br.com.api.techvisit.authentication.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = authException.getCause();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, String> error = new HashMap<>();

        if (cause instanceof TokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            error.put("errorCode", "TOKEN_EXPIRED");
            error.put("message", "Sessão expirada.");
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            error.put("errorCode", "ACCESS_DENIED");
            error.put("message", "Acesso negado.");
        }

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}