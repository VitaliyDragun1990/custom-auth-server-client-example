package org.vdragun.backend.security.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.vdragun.backend.security.model.User;

@Component
public class RestAuthenticationServerProxy implements AuthenticationServerProxy {

    static final String ENDPOINT_AUTH_REQUEST = "/user/auth";

    static final String ENDPOINT_OTP_REQUEST = "/otp/check";

    private final RestTemplate restTemplate;

    private final String baseUrl;

    public RestAuthenticationServerProxy(
            RestTemplate restTemplate,
            @Value("${auth.server.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public void sendAuthenticationRequest(String username, String password) {
        String targetUrl = baseUrl + ENDPOINT_AUTH_REQUEST;

        User requestBody = User.forInitialRequest(username, password);
        HttpEntity<User> request = new HttpEntity<>(requestBody);

        restTemplate.postForEntity(targetUrl, request, Void.class);
    }

    @Override
    public boolean sendOtpCheckRequest(String username, String otpCode) {
        String targetUrl = baseUrl + ENDPOINT_OTP_REQUEST;

        User requestBody = User.forOtpCheck(username, otpCode);
        HttpEntity<User> request = new HttpEntity<>(requestBody);

        ResponseEntity<Void> response = restTemplate.postForEntity(targetUrl, request, Void.class);

        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
