package ge.lchitiashvili.croco_users.services.security;

import ge.lchitiashvili.croco_users.models.security.SecUser;
import ge.lchitiashvili.croco_users.models.security.User;
import ge.lchitiashvili.croco_users.services.users.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;


@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userService.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username"));
//
//        Set<GrantedAuthority> authorities = user.getRoles().stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());

        return new SecUser(user ,convertToGrantedAuthorities(userService.getUserAuthorityNames(user.getId())));
//        new org.springframework.security.core.userdetails.User(
//                usernameOrEmail,
//                user.getPassword(),
//                authorities
//        );
    }


    public static List<GrantedAuthority> convertToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    @NotNull
    public static SecUser getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SecUser)
            return (SecUser) auth.getPrincipal();
        else
            return new SecUser(new User());
    }

    public static boolean hasAuthority(String authority) {
        return hasAnyAuthority(authority);
    }

    public static boolean hasAnyAuthority(String... authorities) {
        Set<String> authoritiesSet = Stream.of(authorities)
                .collect(Collectors.toSet());

        return getUser().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authoritiesSet::contains);
    }



    public static @NotNull SecUser getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new RuntimeException("Not authorised");
        }
        return (SecUser) auth.getPrincipal();
    }

    public static long getCurrentUserId() {
        var user = getCurrentUser();
        return user.getId();
    }

    public String getEncodedPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void changePassword(String oldPassword, String newPassword) {
        Long userId=getCurrentUserId();
        User user = userService.get(userId);
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            throw new RuntimeException("invalid old password");
        }
        user.setPassword(getEncodedPassword(newPassword));
        userService.save(user);
    }

}
