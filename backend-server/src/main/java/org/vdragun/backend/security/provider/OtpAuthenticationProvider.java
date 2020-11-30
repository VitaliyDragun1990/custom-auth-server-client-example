package org.vdragun.backend.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.vdragun.backend.security.authentication.OtpAuthentication;
import org.vdragun.backend.security.proxy.AuthenticationServerProxy;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy authServerProxy;

    public OtpAuthenticationProvider(AuthenticationServerProxy authServerProxy) {
        this.authServerProxy = authServerProxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otpCode = String.valueOf(authentication.getCredentials());

        boolean result = authServerProxy.sendOtpCheckRequest(username, otpCode);

        if (result) {
            return new OtpAuthentication(username, otpCode);
        }

        throw new BadCredentialsException("Bad credentials.");
    }

    @Override
    public boolean supports(Class<?> authenticationClz) {
        return OtpAuthentication.class.isAssignableFrom(authenticationClz);
    }
}
