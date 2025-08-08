package io.github.eglecia.sblibrary.service;

import io.github.eglecia.sblibrary.exceptions.OperationNotPermitted;
import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.repository.AuthorRepository;
import io.github.eglecia.sblibrary.repository.BookRepository;
import io.github.eglecia.sblibrary.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorValidator validator;
    private final BookRepository bookRepository;

// !!! Quando utilizamos RequiredArgsConstructor, o Spring cria um construtor com todos os campos final !!!
//    public AuthorService(AuthorRepository repository,
//                         AuthorValidator authorValidator,
//                         BookRepository bookRepository) {
//        this.repository = repository;
//        this.validator = authorValidator;
//        this.bookRepository = bookRepository;
//    }

    public Author save(Author author) {
        validator.validate(author);
        // Deixe a exceção ser lançada pelo Spring Data JPA, tratando em nível superior
        return repository.save(author);
    }

    public void update(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Author ID must not be null for update.");
        }
        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        // Retorna um Optional, que pode ser vazio se o autor não for encontrado
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        Optional<Author> optAuthor = findById(id);
        Author author = optAuthor.orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));

        if(areThereBooks(author)) {
            throw new OperationNotPermitted("Cannot delete author with associated books.");
        }

        repository.deleteById(id);
    }

    public void delete(Author author) {
        if(areThereBooks(author)) {
            throw new OperationNotPermitted("Cannot delete author with associated books.");
        }

        repository.delete(author);
    }

    public List<Author> find(String name, String nationality) {
        if(name != null && nationality != null) {
            return repository.findByNameAndNationality(name, nationality);
        }
        else if(name != null) {
            return repository.findByName(name);
        }
        else if(nationality != null) {
            return repository.findByNationality(nationality);
        }
        else {
            return repository.findAll();
        }
    }

    public boolean areThereBooks(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
