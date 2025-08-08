package io.github.eglecia.sblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Interface que define métodos CRUD e + alguns úteis
import io.github.eglecia.sblibrary.model.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    List<Author> findByName(String name);
    List<Author> findByNationality(String nationality);
    List<Author> findByNameAndNationality(String name, String nationality);

    Optional<Author> findByNameAndDtBirthdayAndNationality(
            String name, LocalDate dtBirthday, String nationality);
}
