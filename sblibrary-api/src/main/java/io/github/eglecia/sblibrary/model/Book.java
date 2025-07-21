package io.github.eglecia.sblibrary.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "book")
@Data // Lombok irá gerar os métodos getters, setters, equals, hashCode e toString automaticamente em tempo de compilação
@ToString
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "isbn", nullable = false, length = 30)
    private String isbn;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "dt_published", nullable = false)
    private LocalDate dtPublished;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false, columnDefinition = "egenre")
    private EBookGenre genre;

    @Column(name = "price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    // Java cria um construtor vazio por padrão
}
