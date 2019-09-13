package aboudev.aut.auth;

import aboudev.aut.entities.Role;
import aboudev.aut.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Integer id;

    private String fullName;

    private String username;

    @JsonIgnore
    private String password;

    private Set<Role> roles;

    private Boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private User user;

    private UserPrincipal(Integer id, String fullName, String username, String password, Set<Role> roles,
                          boolean enabled, Collection<? extends GrantedAuthority> authorities, User user) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
        this.authorities = authorities;
        this.user = user;
    }

    public static UserPrincipal build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles(),
                user.getEnabled(),
                authorities,
                user
        );
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
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !enabled;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
