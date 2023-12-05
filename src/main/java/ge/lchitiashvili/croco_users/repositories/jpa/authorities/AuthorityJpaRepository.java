package ge.lchitiashvili.croco_users.repositories.jpa.authorities;


import ge.lchitiashvili.croco_users.config.BaseRepository;
import ge.lchitiashvili.croco_users.models.security.Authority;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityJpaRepository extends BaseRepository<Authority,Long> {
}
