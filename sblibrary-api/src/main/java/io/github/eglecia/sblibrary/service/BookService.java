package io.github.eglecia.sblibrary.service;

import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.model.EBookGenre;
import io.github.eglecia.sblibrary.repository.BookRepository;
import io.github.eglecia.sblibrary.repository.specs.BookSpecs;
import io.github.eglecia.sblibrary.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    // O Spring irá injetar automaticamente a instância do BookRepository aqui
    // Isso é possível graças à anotação @RequiredArgsConstructor do Lombok, que gera
    // um construtor com todos os campos finais (final) como parâmetros.
    private final BookRepository bookRepository;
    private final BookValidator validator;

    public Book save(Book book) {
        try {
            validator.validate(book);
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new RegistryDuplicatedException("Livro duplicado: " + e.getMostSpecificCause().getMessage());
        }
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public Page<Book> find(
            String isbn,
            String title,
            String authorName,
            EBookGenre genre,
            Integer yearPublished,
            Integer page,
            Integer pageSize) {

        Specification<Book> spec = (root, query, cb) -> cb.conjunction();
        if(isbn != null && !isbn.isBlank()) {
            spec = spec.and(BookSpecs.isbnEqual(isbn));
        }
        if(title != null && !title.isBlank()) {
            spec = spec.and(BookSpecs.titleLike(title));
        }
        if(genre != null) {
            spec = spec.and(BookSpecs.genreEqual(genre));
        }
        if(yearPublished != null) {
            spec = spec.and(BookSpecs.yearPublishedEqual(yearPublished));
        }
        if(authorName != null && !authorName.isBlank()) {
            spec = spec.and(BookSpecs.authorNameLike(authorName));
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return bookRepository.findAll(spec, pageRequest);
    }

    public void update(Book book) {
        if(book.getId() == null) {
            throw new IllegalArgumentException("Book ID must not be null for update.");
        }

        validator.validate(book);
        bookRepository.save(book);
    }
}
