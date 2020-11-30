package org.vdragun.auth.domain.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vdragun.auth.domain.entity.User;

@Component
public class DummyOtpSender implements OtpSender {

    private static final Logger LOG = LoggerFactory.getLogger(DummyOtpSender.class);

    @Override
    public void sendOtp(String otpCode, User recipient) {
        LOG.info("Sending OTP:[{}] to user with username:[{}]", otpCode, recipient.getUsername());
    }
}
