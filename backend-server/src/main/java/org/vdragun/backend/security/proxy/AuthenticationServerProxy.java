package org.vdragun.backend.security.proxy;

public interface AuthenticationServerProxy {

    void sendAuthenticationRequest(String username, String password);

    boolean sendOtpCheckRequest(String username, String otpCode);
}
