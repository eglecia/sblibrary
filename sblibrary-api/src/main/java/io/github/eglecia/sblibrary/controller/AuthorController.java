package io.github.eglecia.sblibrary.controller;

import io.github.eglecia.sblibrary.controller.dto.AuthorDTO;
import io.github.eglecia.sblibrary.controller.mappers.AuthorMapper;
import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController{

    private final AuthorService authorService;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody @Valid AuthorDTO dto) {
        Author author = mapper.toEntity(dto);
        authorService.save(author);

        // Cria o URI do recurso recém-criado
        // Ex: http://localhost:8080/api/v1/authors/{id}
        URI location = createHeaderLocation(author.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> detailAuthor(@PathVariable("id") String id) {
        UUID idAuthor = UUID.fromString(id);

        return authorService
                .findById(idAuthor)
                .map(author -> {
                    AuthorDTO authorDTO = mapper.toDTO(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") String id){
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(idAuthor);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.delete(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAuthors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> result = authorService.findByExample(name, nationality);

        List<AuthorDTO> authorsDTO = result
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(authorsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(
            @PathVariable("id") String id, @RequestBody @Valid AuthorDTO authorDTO) {

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
    }
}
