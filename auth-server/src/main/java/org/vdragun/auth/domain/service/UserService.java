package org.vdragun.auth.domain.service;

import org.vdragun.auth.domain.entity.Otp;
import org.vdragun.auth.domain.entity.User;

public interface UserService {

    void addUser(User user);

    void authUser(User user);

    boolean checkUser(Otp otpToCheck);
}
