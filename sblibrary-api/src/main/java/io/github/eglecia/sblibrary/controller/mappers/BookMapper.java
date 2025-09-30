package io.github.eglecia.sblibrary.controller.mappers;

import io.github.eglecia.sblibrary.controller.dto.RegisterBookDTO;
import io.github.eglecia.sblibrary.controller.dto.ResultBookDTO;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

// A opção uses = {AuthorMapper.class} informa ao MapStruct que ele deve usar o AuthorMapper
// para mapear objetos relacionados ao Author dentro do Book.
// Isso é útil quando o Book contém uma referência a um Author e você deseja que o MapStruct
// utilize o AuthorMapper para converter essa parte do objeto.
@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public abstract class BookMapper {
    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "dtCreated", ignore = true)
    @Mapping(target = "dtUpdated", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "author", expression = "java(authorRepository.findById(dto.idAuthor()).orElse(null))")
    public abstract Book toEntity(RegisterBookDTO dto);

    public abstract ResultBookDTO toDTO(Book book);
}
