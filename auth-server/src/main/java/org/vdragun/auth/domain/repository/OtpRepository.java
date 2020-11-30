package org.vdragun.auth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vdragun.auth.domain.entity.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findByUsername(String username);
}
