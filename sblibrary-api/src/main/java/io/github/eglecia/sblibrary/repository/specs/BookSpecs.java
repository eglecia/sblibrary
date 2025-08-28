package io.github.eglecia.sblibrary.repository.specs;

import io.github.eglecia.sblibrary.model.Book;
import io.github.eglecia.sblibrary.model.EBookGenre;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> genreEqual(EBookGenre genre) {
        return (root, query, cb) -> cb.equal(
                cb.function("text", String.class, root.get("genre")),
                genre.name()
        );
    }

    public static Specification<Book> yearPublishedEqual(Integer yearPublished) {
        //select to_char(dt_published, 'YYYY') from book;
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dtPublished"), cb.literal("YYYY")), yearPublished.toString());
    }

    public static Specification<Book> authorNameLike(String authorName) {
        //select * from book b left join author a on a.id = b.id_author where lower(a.name) like '%nome%';
        return (root, query, cb) -> {
            Join<Object, Object> joinAuthor = root.join("author", JoinType.LEFT);
            return cb.like(cb.lower(joinAuthor.get("name")), "%" + authorName.toLowerCase() + "%");
        };
    }
}
