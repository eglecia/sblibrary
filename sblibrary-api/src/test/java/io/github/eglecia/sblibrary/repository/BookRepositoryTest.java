package io.github.eglecia.sblibrary.repository;

import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.model.EBookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveBookTest() {
        try {
            Book book = new Book();
            book.setTitle("Indiana Jones e o Templo da Perdição");
            book.setIsbn("12345-67891");
            book.setGenre(EBookGenre.FICCAO);
            book.setPrice(BigDecimal.valueOf(30.99));
            book.setDtPublished(LocalDate.of(1980, 7, 12));

            Author author = authorRepository
                    .findById(UUID.fromString("ee49c0c4-f0d2-467e-b26f-cce1e6e47a89"))
                    .orElse(null);

            book.setAuthor(author);

            bookRepository.save(book);

            System.out.println("Save book test executed successfully.");
            System.out.println(book);
        } catch (Exception e) {
            System.out.println("Error saving book: " + e.getMessage());
        }
    }

    @Test
    public void listBooksByAuthorIdTest() {
        try {
            var authorId = UUID.fromString("ee49c0c4-f0d2-467e-b26f-cce1e6e47a89");
            var books = bookRepository.findByAuthorId(authorId);
            System.out.println("Total Books by Author ID " + authorId + ": " + books.size());
            System.out.println("List of Books:");
            books.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error listing books by author ID: " + e.getMessage());
        }
    }

    @Test
    public void listBooksByAuthorIdJPQLTest() {
        try {
            var authorId = UUID.fromString("ee49c0c4-f0d2-467e-b26f-cce1e6e47a89");
            var books = bookRepository.findBooksByAuthorId(authorId);
            System.out.println("Total Books by Author ID " + authorId + ": " + books.size());
            System.out.println("List of Books:");
            books.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error listing books by author ID using JPQL: " + e.getMessage());
        }
    }

    @Test
    public void listGenreEUAAuthorsTest() {
        try {
            var genres = bookRepository.listGenreEUAAuthorsJPQL();
            System.out.println("List of Genres from Authors in the USA:");
            genres.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error listing genres from authors in the USA: " + e.getMessage());
        }
    }

    @Test
    public void listGenreFromAuthorNationalitySQLTest() {
        try {
            var genres = bookRepository.listGenreFromAuthorNationalitySQL("EUA");
            System.out.println("List of Genres from Authors in the USA:");
            genres.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error listing genres from authors in the USA: " + e.getMessage());
        }
    }

    @Test
    public void listBooksFromAuthorNationalitySQLTest() {
        try {
            List<Book> books = bookRepository.listBooksFromAuthorNationalitySQL("EUA");
            System.out.println("List of Books from Authors in the USA:");
            books.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error listing books from authors in the USA: " + e.getMessage());
        }
    }
}
