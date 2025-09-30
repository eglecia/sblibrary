package io.github.eglecia.sblibrary.controller.mappers;

import io.github.eglecia.sblibrary.controller.dto.AuthorDTO;
import io.github.eglecia.sblibrary.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "dtCreated", ignore = true)
    @Mapping(target = "dtUpdated", ignore = true)
    @Mapping(target = "createdBy", ignore = true)

    Author toEntity(AuthorDTO dto);
    AuthorDTO toDTO(Author entity);
}
