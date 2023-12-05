package ge.lchitiashvili.croco_users.services.authorities;



import ge.lchitiashvili.croco_users.dtos.security.AuthorityDTO;
import ge.lchitiashvili.croco_users.models.security.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface AuthorityService {
    List<Authority> search(String name);

    Authority get(long id);

    public List<AuthorityDTO> ENTITY_DTO_List(List<Authority> entities);

    public Page<AuthorityDTO> ENTITY_DTO_PAGE(Page<Authority> entitiesPage);

    public AuthorityDTO ENTITY_DTO(Authority entity);

     AuthorityDTO AUTHORITY_DTO(Authority authority);
}
