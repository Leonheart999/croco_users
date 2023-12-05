package ge.lchitiashvili.croco_users.services.users;


import ge.lchitiashvili.croco_users.config.EntityToDtoConverter;
import ge.lchitiashvili.croco_users.dtos.security.UserDTO;
import ge.lchitiashvili.croco_users.models.security.Authority;
import ge.lchitiashvili.croco_users.models.security.User;
import ge.lchitiashvili.croco_users.models.security.UserAuthority;
import ge.lchitiashvili.croco_users.repositories.jpa.authorities.UserAuthorityJpaRepository;
import ge.lchitiashvili.croco_users.repositories.jpa.users.UserJPARepository;
import ge.lchitiashvili.croco_users.services.authorities.AuthorityService;
import ge.lchitiashvili.croco_users.services.security.SecurityService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends EntityToDtoConverter<User, UserDTO> implements UserService {
    private final UserJPARepository userRepository;
    @Lazy
    private final SecurityService securityService;
    private final AuthorityService authorityService;
    private final UserAuthorityJpaRepository userAuthorityRepository;


    @Override
    public List<User> search(String privateNumber, String email, Boolean onlyActive, User.Type type) {
        return userRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (BooleanUtils.isTrue(onlyActive)) {
                predicate = cb.and(predicate, cb.equal(root.get(User.Fields.active), true));
            }
            if (!StringUtils.isEmpty(privateNumber)) {
                predicate = cb.and(predicate, cb.like(root.get(User.Fields.privateNumber), "%" + privateNumber + "%"));
            }

            if (!StringUtils.isEmpty(email)) {
                predicate = cb.and(predicate, cb.like(root.get(User.Fields.email), "%" + email + "%"));
            }

            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get(User.Fields.type), type));
            }

            return predicate;
        });
//        return userRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", key = "#id")
    public User delete(long id) {
        User user = get(id);
        user.setActive(false);
        user = userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "user", key = "#id")
    public User get(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException(String.format("User with with id %s", id));
        }
        return user.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User addNew(User user) {
        user.setActive(true);
        user.setPassword(securityService.getEncodedPassword(user.getPassword()));
        return save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", key = "#id")
    public User edit(long id, User user) {
        User oldUser = get(id);
        user.setId(id);
        user.setPassword(oldUser.getPassword());
        return save(user);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmailAndActiveTrue(usernameOrEmail, usernameOrEmail);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        securityService.changePassword(oldPassword, newPassword);
    }

    @Override
    public List<String> getUserAuthorityNames(long userId) {
        return userRepository.findUserAuthorities(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserAuthority(long id, long authorityId) {
        get(id);
        authorityService.get(authorityId);
        UserAuthority userAuthority = userAuthorityRepository.findFirstByUserIdAndAuthorityId(id, authorityId);
        if (userAuthority != null) {
            userAuthority.setActive(true);
        } else {
            userAuthority = new UserAuthority();
            userAuthority.setAuthorityId(authorityId);
            userAuthority.setUserId(id);
            userAuthority.setActive(true);
        }
        userAuthorityRepository.save(userAuthority);
    }

    @Override
    public List<Authority> getUserAuthorities(long id) {
        List<UserAuthority> userAuthorities = userAuthorityRepository.findByUserIdAndActiveTrue(id);
        //
        return userAuthorities.stream().map(UserAuthority::getAuthority).collect(Collectors.toList());
    }
}
