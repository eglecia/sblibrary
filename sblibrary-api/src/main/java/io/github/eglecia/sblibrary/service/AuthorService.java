package io.github.eglecia.sblibrary.service;

import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        // Deixe a exceção ser lançada pelo Spring Data JPA, tratando em nível superior
        return repository.save(author);
    }
}
