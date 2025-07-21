package io.github.eglecia.sblibrary.service;

import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.repository.AuthorRepository;
import io.github.eglecia.sblibrary.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalDB {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void saveAuthorAndBook(Author author, Book book) {
        try {
            authorRepository.save(author);
            System.out.println("Author saved successfully: " + author);
        } catch (Exception e) {
            System.out.println("Transaction error. Try save author: " + e.getMessage());
            throw new RuntimeException("Failed to save author", e);
        }

        try {
            bookRepository.save(book);
            System.out.println("Book saved successfully: " + book);
        } catch (Exception e) {
            System.out.println("Transaction error. Try save book: " + e.getMessage());
            throw new RuntimeException("Failed to save book", e);
        }
    }
}
