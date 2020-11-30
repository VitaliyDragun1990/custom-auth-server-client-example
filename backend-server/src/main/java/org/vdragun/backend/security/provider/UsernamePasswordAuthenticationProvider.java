package org.vdragun.backend.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.vdragun.backend.security.authentication.UsernamePasswordAuthentication;
import org.vdragun.backend.security.proxy.AuthenticationServerProxy;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy authServerProxy;

    public UsernamePasswordAuthenticationProvider(AuthenticationServerProxy authServerProxy) {
        this.authServerProxy = authServerProxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        authServerProxy.sendAuthenticationRequest(username, password);

        return new UsernamePasswordAuthentication(username, password);
    }

    @Override
    public boolean supports(Class<?> authenticationClz) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authenticationClz);
    }
}
