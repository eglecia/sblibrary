package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.RegisterBookDTO;
import io.github.eglecia.sblibrary.controller.dto.ResultBookDTO;
import io.github.eglecia.sblibrary.controller.mappers.BookMapper;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController implements GenericController{
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid RegisterBookDTO bookDTO) {
        // Mapear DTO para entidade
        Book book = bookMapper.toEntity(bookDTO);
        // Salvar o objeto criado
        bookService.save(book);
        // Criar o URI do recurso recém-criado
        var url = createHeaderLocation(book.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultBookDTO> detailBook(@PathVariable("id") String id) {
        UUID idBook = UUID.fromString(id);

        return bookService
                .findById(idBook)
                .map(book -> {
                    ResultBookDTO bookDTO = bookMapper.toDTO(book);
                    return ResponseEntity.ok(bookDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
