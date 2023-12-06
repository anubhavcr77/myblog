package com.myblog.repository;

import com.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByUsername(String userName);
    Optional<User>findByEmail(String email);
    Optional<User>findByUsernameOrEmail(String userName, String email);
    Boolean existsByUsername(String userName);
    Boolean existsByEmail(String email);
}
