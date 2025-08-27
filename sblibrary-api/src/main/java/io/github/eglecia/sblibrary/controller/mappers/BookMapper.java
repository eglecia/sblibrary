package io.github.eglecia.sblibrary.controller.mappers;

import io.github.eglecia.sblibrary.controller.dto.RegisterBookDTO;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "dtCreated", ignore = true)
    @Mapping(target = "dtUpdated", ignore = true)
    @Mapping(target = "author", expression = "java(authorRepository.findById(dto.idAuthor()).orElse(null))")
    public abstract  Book toEntity(RegisterBookDTO dto);
    //RegisterBookDTO toDTO(Book entity);
}
