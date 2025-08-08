package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.AuthorDTO;
import io.github.eglecia.sblibrary.controller.dto.ResponseError;
import io.github.eglecia.sblibrary.exceptions.OperationNotPermitted;
import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @PostMapping
    public ResponseEntity<Object> createAuthor(@RequestBody AuthorDTO author) {
        try {
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
        } catch (RegistryDuplicatedException e) {
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> detailAuthor(@PathVariable("id") String id) {
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(idAuthor);
        if(authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO authorDTO = new AuthorDTO(
                    author.getId(),
                    author.getName(),
                    author.getDtBirthday(),
                    author.getNationality()
            );
            return ResponseEntity.ok(authorDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") String id){
        try {
            UUID idAuthor = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(idAuthor);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            authorService.delete(authorOptional.get());
            return ResponseEntity.noContent().build();
        } catch(OperationNotPermitted e) {
            var errorResp = ResponseError.defaultResponse((e.getMessage()));
            return ResponseEntity.status(errorResp.status()).body(errorResp);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> result = authorService.find(name, nationality);

        List<AuthorDTO> authorsDTO = result.stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getDtBirthday(),
                        author.getNationality()))
                .toList();
        return ResponseEntity.ok(authorsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(
            @PathVariable("id") String id,  @RequestBody AuthorDTO authorDTO) {
        try {
            UUID idAuthor = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(idAuthor);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Author author = authorOptional.get();
            author.setName(authorDTO.name());
            author.setDtBirthday(authorDTO.dtBirthday());
            author.setNationality(authorDTO.nationality());
            authorService.update(author);

            return ResponseEntity.noContent().build();
        } catch(RegistryDuplicatedException e) {
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
