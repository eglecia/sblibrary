package io.github.eglecia.sblibrary.controller.dto;

import io.github.eglecia.sblibrary.model.EBookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate dtPublished,
        EBookGenre genre,
        BigDecimal price,
        AuthorDTO author
        ) {
}
