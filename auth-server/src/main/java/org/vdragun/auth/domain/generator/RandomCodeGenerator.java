package org.vdragun.auth.domain.generator;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class RandomCodeGenerator implements CodeGenerator {

    @Override
    public String generateCode() {
        int code = generateRandomFourDigitCode();

        return String.valueOf(code);
    }

    private int generateRandomFourDigitCode() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem with generating code:" + e.getMessage(), e);
        }
        return random.nextInt(9000) + 1000;
    }
}
