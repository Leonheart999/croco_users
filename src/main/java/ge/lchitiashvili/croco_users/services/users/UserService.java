package ge.lchitiashvili.croco_users.services.users;

import ge.lchitiashvili.croco_users.dtos.security.AuthorityDTO;
import ge.lchitiashvili.croco_users.dtos.security.UserDTO;
import ge.lchitiashvili.croco_users.models.security.Authority;
import ge.lchitiashvili.croco_users.models.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> search(String privateNumber,  String email, Boolean onlyActive, User.Type type);

    User delete(long id);

    User get(long id);

    User save(User user);

    User edit(long id,User user);

    User addNew(User user);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    void changePassword(String oldPassword, String newPassword);

    List<String> getUserAuthorityNames(long userId);

    void addUserAuthority(long id, long authorityId);

    List<Authority> getUserAuthorities(long id);

     List<UserDTO> ENTITY_DTO_List(List<User> entities);

     Page<UserDTO> ENTITY_DTO_PAGE(Page<User> entitiesPage) ;
     UserDTO ENTITY_DTO(User entity);
    
}
