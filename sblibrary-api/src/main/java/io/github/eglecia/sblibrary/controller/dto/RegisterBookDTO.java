package io.github.eglecia.sblibrary.controller.dto;

import io.github.eglecia.sblibrary.model.EBookGenre;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterBookDTO(
        UUID id,
        @ISBN
        @NotBlank(message = "ISBN is mandatory")
        String isbn,
        @NotBlank(message = "ISBN is mandatory")
        String title,
        @NotNull(message = "ISBN is mandatory")
        @Past(message = "Published date must be a past date")
        LocalDate dtPublished,
        EBookGenre genre,
        BigDecimal price,
        @NotNull(message = "ISBN is mandatory")
        UUID idAuthor
        ) {
}
