package ge.lchitiashvili.croco_users.models.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Data
@Table(name = "authorities")
@FieldNameConstants
@SequenceGenerator(name = "authorityIdSeq", sequenceName = "authorities_id_seq", allocationSize = 1)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "active")
    private Boolean active;
    @JsonManagedReference("Authority")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH,mappedBy = "parent")
    @Where(clause = "active = 'true'")
    @BatchSize(size = 200)
    private List<Authority> children;
    @JsonBackReference("Authority")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "active = 'true'")
    public Authority parent;
}
