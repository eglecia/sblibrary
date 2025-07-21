package io.github.eglecia.sblibrary.repository;

import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.model.EBookGenre;
import io.github.eglecia.sblibrary.service.TransactionalDB;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class TransactionalTest {
    @Autowired
    private TransactionalDB transactionalDB;

    @Test
    public void execute() {
        Author author = new Author();
        author.setName("Mauricio de Sousa");
        author.setNationality("Brasileiro");
        author.setDtBirthday(LocalDate.of(1930, 6, 15));

        Book book = new Book();
        book.setTitle("Turma da Mônica");
        book.setIsbn("12345-67893");
        book.setGenre(EBookGenre.FANTASIA);
        book.setPrice(BigDecimal.valueOf(9.99));
        book.setDtPublished(LocalDate.of(1980, 7, 19));
        book.setAuthor(author);

        try {
            transactionalDB.saveAuthorAndBook(author, book);
        } catch (RuntimeException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}
