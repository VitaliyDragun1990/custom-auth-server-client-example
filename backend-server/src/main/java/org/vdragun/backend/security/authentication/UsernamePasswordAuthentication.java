package org.vdragun.backend.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Authentication implementation for the first step of MFA
 */
public class UsernamePasswordAuthentication  extends UsernamePasswordAuthenticationToken {

    /**
     * When created with this constructor authentication instance remains unauthenticated
     */
    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    /**
     * When created with this constructor authentication instance becomes authenticated (authentication process ends)
     */
    public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
