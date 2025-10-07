package io.github.eglecia.sblibrary.repository;

import io.github.eglecia.sblibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);

    User findByEmail(String email);
}
