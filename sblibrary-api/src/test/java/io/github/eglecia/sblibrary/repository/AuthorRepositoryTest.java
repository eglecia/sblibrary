package io.github.eglecia.sblibrary.repository;

import io.github.eglecia.sblibrary.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository repository;

    @Test
    public void saveAuthorTest() {
        try {
            Author author = new Author();
            author.setName("George Lucas");
            author.setNationality("EUA");
            author.setDtBirthday(LocalDate.of(1950, 4, 26));

            var savedAuthor = repository.save(author);
            System.out.println("Saved Author: " + savedAuthor);
        } catch (Exception e) {
            System.out.println("Error saving author: " + e.getMessage());
        }
    }

    @Test
    public void updateAuthorTest() {
        try {
            var uuid = UUID.fromString("3a4a1bca-987e-4f02-b945-86d39f573836");

            Optional<Author> author_db = repository.findById(uuid);
            if (author_db.isPresent()) {
                Author author = author_db.get();
                System.out.println("Author found: " + author);
                author.setDtBirthday(LocalDate.of(1960, 6, 19));
                repository.save(author);
            }
            else {
                System.out.println("Author not found with UUID: " + uuid);
            }

        } catch (Exception e) {
            System.out.println("Error updating author: " + e.getMessage());
        }
    }

    @Test
    public void listAuthorsTest() {
        try {
            List<Author> authors = repository.findAll();
            System.out.println("Total Authors: " + authors.size());
            System.out.println("List of Authors:");
            authors.forEach(System.out::println);;
        } catch (Exception e) {
            System.out.println("Error listing authors: " + e.getMessage());
        }
    }

    @Test
    public void deleteAuthorTest() {
        try {
            var uuid = UUID.fromString("38bc60db-4e6d-4aa4-a238-f5f9b831ae8c");

            // Verifica se o autor existe antes de tentar excluir
            boolean existsBeforeDelete = repository.existsById(uuid);

            if (existsBeforeDelete) {
                repository.deleteById(uuid);
                System.out.println("Autor com ID " + uuid + " foi excluído com sucesso");
            } else {
                System.out.println("Autor com ID " + uuid + " não encontrado, nenhum registro removido");
            }
        } catch (Exception e) {
            System.out.println("Error deleting author: " + e.getMessage());
        }
    }
}
