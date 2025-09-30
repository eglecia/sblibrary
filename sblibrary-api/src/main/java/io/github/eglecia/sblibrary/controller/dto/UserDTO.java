package io.github.eglecia.sblibrary.controller.dto;

import java.util.List;
import java.util.UUID;

public record UserDTO(UUID id, String login, String password, List<String> roles) {
}
