package io.github.eglecia.sblibrary.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        @NotBlank (message = "Login cannot be blank")
        String login,
        @Email (message = "Invalid email format")
        @NotBlank (message = "Email cannot be blank")
        String email,
        @NotBlank (message = "Password cannot be blank")
        String password,
        List<String> roles) {
}
