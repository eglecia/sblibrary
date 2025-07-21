package io.github.eglecia.sblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Interface que define métodos CRUD e + alguns úteis
import io.github.eglecia.sblibrary.model.Author;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
