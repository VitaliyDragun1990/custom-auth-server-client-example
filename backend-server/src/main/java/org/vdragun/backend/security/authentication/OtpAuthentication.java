package org.vdragun.backend.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Authentication implementation for the second step of MFA
 */
public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

    /**
     * When created with this constructor authentication instance remains unauthenticated
     */
    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    /**
     * When created with this constructor authentication instance becomes authenticated (authentication process ends)
     */
    public OtpAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
