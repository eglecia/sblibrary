package io.github.eglecia.sblibrary.service;

import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    // O Spring irá injetar automaticamente a instância do BookRepository aqui
    // Isso é possível graças à anotação @RequiredArgsConstructor do Lombok, que gera
    // um construtor com todos os campos finais (final) como parâmetros.
    private final BookRepository bookRepository;

    public Book save(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new RegistryDuplicatedException("Livro duplicado: " + e.getMostSpecificCause().getMessage());
        }
    }
}
