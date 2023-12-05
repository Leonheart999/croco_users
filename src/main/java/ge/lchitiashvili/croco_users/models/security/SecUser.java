package ge.lchitiashvili.croco_users.models.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class SecUser implements UserDetails {

    private final Long id;
    private final User.Type type;
    private final String username;
    private final String email;
    private final String password;
    private final boolean enabled;
    private final Collection<GrantedAuthority> authorities;

    public SecUser(User user,  List<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.type = user.getType();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.getActive();
        this.email = user.getEmail();
        this.authorities = authorities;
    }

    public SecUser(User user) {
        this.id = user.getId();
        this.type = user.getType();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.getActive();
        this.email = user.getEmail();
        this.authorities = List.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User.Type getType() {
        return type;
    }
}
