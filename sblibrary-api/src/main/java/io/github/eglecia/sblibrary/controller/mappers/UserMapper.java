package io.github.eglecia.sblibrary.controller.mappers;

import io.github.eglecia.sblibrary.controller.dto.UserDTO;
import io.github.eglecia.sblibrary.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
}
