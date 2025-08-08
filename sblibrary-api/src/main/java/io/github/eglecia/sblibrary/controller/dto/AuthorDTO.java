package io.github.eglecia.sblibrary.controller.dto;

import io.github.eglecia.sblibrary.model.Author;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.*;

public record AuthorDTO(UUID id,
                        @NotBlank(message = "Name is mandatory")
                        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
                        String name,
                        @NotNull(message = "Birthday is mandatory")
                        @Past(message = "Birthday must be a past date")
                        LocalDate dtBirthday,
                        @NotBlank(message = "Nacionality is mandatory")
                        String nationality) {

    public Author toAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setDtBirthday(this.dtBirthday);
        author.setNationality(this.nationality);
        return author;
    }
}
