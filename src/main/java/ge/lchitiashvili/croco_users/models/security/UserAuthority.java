package ge.lchitiashvili.croco_users.models.security;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Data
@Table(name = "user_authorities")
@SequenceGenerator(name = "userAuthorityIdSeq", sequenceName = "user_authorities_id_seq", allocationSize = 1)
public class UserAuthority {
    @Id
    @GeneratedValue(generator = "userAuthorityIdSeq",strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "authority_id", nullable = false)
    private Long authorityId;
    @Column(name = "active")
    private Boolean active;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "authority_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "active = 'true'")
    public Authority authority;
}
