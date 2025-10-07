package io.github.eglecia.sblibrary.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;
    @Column
    private String login;
    @Column
    private String email;
    @Column
    private String password;
    @Type(ListArrayType.class)
    @Column(name="roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
