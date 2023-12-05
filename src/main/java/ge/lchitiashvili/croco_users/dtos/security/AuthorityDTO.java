package ge.lchitiashvili.croco_users.dtos.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.util.List;

@Data
public class AuthorityDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Boolean active;
    private List<AuthorityDTO> children;
}
