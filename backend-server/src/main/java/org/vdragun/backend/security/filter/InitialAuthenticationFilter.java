package org.vdragun.backend.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vdragun.backend.security.authentication.UsernamePasswordAuthentication;
import org.vdragun.backend.security.token.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authManager;

    private final JwtTokenService jwtTokenService;

    public InitialAuthenticationFilter(
            AuthenticationManager authManager,
            JwtTokenService jwtTokenService) {
        this.authManager = authManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        // if the code is absent - it is the first authentication step
        if (code == null) {
            Authentication auth = new UsernamePasswordAuthentication(username, password);
            authManager.authenticate(auth);
        }
        // If the code is present = it is the second authentication step
        else {
            String jwt = jwtTokenService.buildJwtToken(Map.of("username", username));
            response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
        }
    }

    /**
     * Filter should only be applied to '/login' URL
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
