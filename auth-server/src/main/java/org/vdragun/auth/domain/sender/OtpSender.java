package org.vdragun.auth.domain.sender;

import org.vdragun.auth.domain.entity.User;

public interface OtpSender {

    void sendOtp(String otpCode, User recipient);
}
