package ge.lchitiashvili.croco_users.models.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
@SequenceGenerator(name = "userIdSeq", sequenceName = "users_id_seq", allocationSize = 1)
@FieldNameConstants
public class User implements Serializable {
    public enum Type{
        USER, ADMIN
    }
    @Id
    @GeneratedValue(generator = "userIdSeq",strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "private_number", nullable = false)
    private String privateNumber;
    @Column(name = "password", nullable = false)
    @JsonBackReference
    private String password;
    @Column(name = "email", unique = true )
    private String email;
    @Column(name = "active")
    private Boolean active;
}
