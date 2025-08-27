package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.ResponseError;
import io.github.eglecia.sblibrary.controller.mappers.BookMapper;
import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import io.github.eglecia.sblibrary.model.Book;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import io.github.eglecia.sblibrary.service.BookService;
import io.github.eglecia.sblibrary.controller.dto.RegisterBookDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController implements GenericController{
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
        try {
            // Mapear DTO para entidade
            Book book = bookMapper.toEntity(bookDTO);
            // Salvar o objeto criado
            bookService.save(book);
            // Criar o URI do recurso recém-criado
            var url = createHeaderLocation(book.getId());

            return ResponseEntity.created(url).build();
        } catch (RegistryDuplicatedException e) {
            var errorDTO = ResponseError.conflictResponse(e.getMessage());

            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
