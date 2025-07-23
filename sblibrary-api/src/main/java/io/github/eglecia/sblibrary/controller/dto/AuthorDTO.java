package io.github.eglecia.sblibrary.controller.dto;

import io.github.eglecia.sblibrary.model.Author;

import java.time.LocalDate;

public record AuthorDTO(String name,
                        LocalDate dtBirthday,
                        String nationality) {

    public Author toAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setDtBirthday(this.dtBirthday);
        author.setNationality(this.nationality);
        return author;
    }
}
