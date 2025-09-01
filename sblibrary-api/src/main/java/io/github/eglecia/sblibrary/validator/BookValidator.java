package io.github.eglecia.sblibrary.validator;

import io.github.eglecia.sblibrary.exceptions.InvalidFieldException;
import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private static final int PRICE_MANDATORY_YEAR = 2020;
    private final BookRepository bookRepository;

    public void validate(Book book) {
        if(validateIsbn(book)) {
            throw new RegistryDuplicatedException("ISBN must be unique");
        }

        if(validateNullPrice(book)) {
            throw new InvalidFieldException("price", "Price cannot be null");
        }
    }

    private boolean validateNullPrice(Book book) {
        return book.getDtPublished().getYear() >= PRICE_MANDATORY_YEAR && book.getPrice() == null;
    }

    public boolean validateIsbn(Book book) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());

        // Se o ID do livro for nulo, significa que é um novo livro (inserção)
        if(book.getId() == null) {
            // Verifica se já existe um livro com o mesmo ISBN
            return bookOptional.isPresent();
        } else {
            //return bookOptional.isPresent() && !bookOptional.get().getId().equals(book.getId());
            //ou
            return bookOptional
                    .map(Book::getId)
                    .stream()
                    .anyMatch(id -> !id.equals(book.getId()));
        }
    }

    //public boolean
}
