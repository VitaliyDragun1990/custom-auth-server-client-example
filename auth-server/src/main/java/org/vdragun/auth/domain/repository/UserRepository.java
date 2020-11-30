package org.vdragun.auth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vdragun.auth.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
}
