package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.UserDTO;
import io.github.eglecia.sblibrary.controller.mappers.UserMapper;
import io.github.eglecia.sblibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDTO userDTO) {
        var user = userMapper.toEntity(userDTO);
        userService.save(user);
    }
}
