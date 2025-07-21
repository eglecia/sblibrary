package io.github.eglecia.sblibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "author")
@Getter // Plugin Lombok irá gerar os métodos getters e setters automaticamente em tempo de compilação
@Setter
public class Author {
    @Id
    @GeneratedValue // O banco já gerar essa valor por default
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "dt_birthday", nullable = false)
    private LocalDate dtBirthday;

    @Column(name = "nationality", nullable = false, length = 50)
    private String nationality;

    //Java cria um construtor vazio por padrão
}
