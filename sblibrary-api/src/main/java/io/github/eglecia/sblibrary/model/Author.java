package io.github.eglecia.sblibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity // Classe é uma entidade JPA mapeada para uma tabela no banco de dados
@Table(schema = "public", name = "author")
@Getter // Lombok: irá gerar os métodos getters automaticamente em tempo de compilação
@Setter // Lombok: irá gerar os métodos setters automaticamente em tempo de compilação
@ToString // Lombok: gera automaticamente o metodo toString para a classe
@EntityListeners(AuditingEntityListener.class) // Configura a classe para usar o listener de auditoria do Spring Data JPA
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

    @CreatedDate
    @Column(name = "dt_created")
    private LocalDateTime dtCreated;

    @LastModifiedDate
    @Column(name = "dt_updated")
    private LocalDateTime dtUpdated;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User createdBy;

    //Java cria um construtor vazio por padrão
}
