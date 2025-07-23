package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.AuthorDTO;
import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorDTO author) {
        Author authorEntity = author.toAuthor();
        authorService.save(authorEntity);

        // Cria o URI do recurso recém-criado
        // Ex: http://localhost:8080/api/v1/authors/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
