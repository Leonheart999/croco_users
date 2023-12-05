package ge.lchitiashvili.croco_users.repositories.jpa.authorities;

import ge.lchitiashvili.croco_users.config.BaseRepository;
import ge.lchitiashvili.croco_users.models.security.UserAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthorityJpaRepository extends BaseRepository<UserAuthority,Long> {

    UserAuthority findFirstByUserIdAndAuthorityId(long userId, long authorityId);

    List<UserAuthority> findByUserIdAndActiveTrue(long id);
}
