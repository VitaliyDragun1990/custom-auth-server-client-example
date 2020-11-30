package org.vdragun.auth.domain.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vdragun.auth.domain.entity.Otp;
import org.vdragun.auth.domain.entity.User;
import org.vdragun.auth.domain.generator.CodeGenerator;
import org.vdragun.auth.domain.repository.OtpRepository;
import org.vdragun.auth.domain.repository.UserRepository;
import org.vdragun.auth.domain.sender.OtpSender;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final CodeGenerator codeGenerator;

    private final OtpSender otpSender;

    private final UserRepository userRepository;

    private final OtpRepository otpRepository;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            CodeGenerator codeGenerator,
            OtpSender otpSender, UserRepository userRepository,
            OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.codeGenerator = codeGenerator;
        this.otpSender = otpSender;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void authUser(User user) {
        User matchingUser = findUserByUsername(user.getUsername());

        assertPasswordMatch(user.getPassword(), matchingUser.getPassword());

        String otpCode = renewOtp(matchingUser);

        otpSender.sendOtp(otpCode, user);
    }

    @Override
    public boolean checkUser(Otp otpToCheck) {
        Optional<Otp> userOtpOptional = otpRepository.findByUsername(otpToCheck.getUsername());

        if (userOtpOptional.isPresent()) {
            Otp userOtp = userOtpOptional.get();
            return Objects.equals(userOtp.getCode(), otpToCheck.getCode());
        }

        return false;
    }

    private String renewOtp(User user) {
        String otpCode = codeGenerator.generateCode();

        Optional<Otp> userOtp = otpRepository.findByUsername(user.getUsername());

        if (userOtp.isPresent()) {
            updateUserOtp(userOtp.get(), otpCode);
        } else {
            createNewOtpForUser(user, otpCode);
        }

        return otpCode;
    }

    private void updateUserOtp(Otp otp, String newCode) {
        otp.setCode(newCode);
    }

    private void createNewOtpForUser(User user, String otpCode) {
        Otp otp = new Otp(user.getUsername(), otpCode);
        otpRepository.save(otp);
    }

    private void assertPasswordMatch(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    private User findUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
    }

}
