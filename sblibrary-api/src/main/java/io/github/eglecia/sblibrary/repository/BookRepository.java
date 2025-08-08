package io.github.eglecia.sblibrary.repository;

import io.github.eglecia.sblibrary.model.Author;
import io.github.eglecia.sblibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    // Seguindo esse padrão de nomenclatura, o Spring Data JPA irá gerar a consulta automaticamente
    // Query Method: findByAuthorId
    List<Book> findByAuthorId(UUID authorId);

    boolean existsByAuthor(Author author);

    // JPQL -> Formato sql mas referenciando as entidades e propriedades
    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findBooksByAuthorId(UUID authorId);

    @Query("""
              SELECT DISTINCT b.genre
              FROM Book b
              JOIN b.author a
              WHERE a.nationality = 'EUA'
              ORDER BY b.genre
    """)
    List<String> listGenreEUAAuthorsJPQL();

    @Query(value = """
            SELECT DISTINCT b.genre
            FROM book b
            INNER JOIN author a ON a.id = b.id_author
            WHERE a.nationality = :nat
            ORDER BY b.genre
            """,
            nativeQuery = true)
    List<String> listGenreFromAuthorNationalitySQL(@Param("nat") String nationality);

    @Query(value = """
            SELECT b.*
            FROM book b
            INNER JOIN author a ON a.id = b.id_author
            WHERE a.nationality = :nat
            ORDER BY b.title
            """,
            nativeQuery = true)
    List<Book> listBooksFromAuthorNationalitySQL(@Param("nat") String nationality);
}
