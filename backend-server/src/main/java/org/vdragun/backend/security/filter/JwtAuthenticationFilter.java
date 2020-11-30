package org.vdragun.backend.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vdragun.backend.security.authentication.UsernamePasswordAuthentication;
import org.vdragun.backend.security.token.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        Map<String, Object> claims = jwtTokenService.parseJwtToken(jwt);

        GrantedAuthority authority = new SimpleGrantedAuthority("user");
        String username = String.valueOf(claims.get("username")); // dummy authority

        Authentication auth = new UsernamePasswordAuthentication(username, null, List.of(authority));

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    /**
     * Filter should not be applied to '/login' URL
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
