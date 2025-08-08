package io.github.eglecia.sblibrary.validator;

import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository repository;

    public AuthorValidator (AuthorRepository authorRepository) {
        this.repository = authorRepository;
    }

    public void validate(Author author) {
        if(isRegistered(author)) {
            throw new RegistryDuplicatedException(
                            "Author with name '" + author.getName() +
                            "', birthday '" + author.getDtBirthday() +
                            "', nationality '" + author.getNationality() +
                            "' is already registered.");
        }
    }

    private boolean isRegistered(Author author) {
        Optional<Author> authorOptional = repository.findByNameAndDtBirthdayAndNationality(
                author.getName(), author.getDtBirthday(), author.getNationality());

        if(author.getId() == null) {
            return authorOptional.isPresent();
        }

        return authorOptional.isPresent() && !authorOptional.get().getId().equals(author.getId());
    }
}
