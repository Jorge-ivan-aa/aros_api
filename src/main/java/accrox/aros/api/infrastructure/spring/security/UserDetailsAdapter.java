package accrox.aros.api.infrastructure.spring.security;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsAdapter implements UserDetails {

    private User data;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsAdapter(User user, boolean isAdmin) {
        this.data = user;
        this.authorities = buildAuthorities(user, isAdmin);
    }

    public UserDetailsAdapter(
        User user,
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.data = user;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.data.getEmail();
    }

    @Override
    public String getPassword() {
        return this.data.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public User getUser() {
        return data;
    }

    public String getEmail() {
        return this.data.getEmail();
    }

    public String getName() {
        return this.data.getName();
    }

    public Set<Area> getAreas() {
        if (this.data.getAreas() != null) {
            return new HashSet<Area>(this.data.getAreas());
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Build authorities based on user properties and admin status
     * - Admin users get ROLE_ADMIN
     * - Regular users get ROLE_USER
     * - Additional authorities based on areas
     */
    private Collection<? extends GrantedAuthority> buildAuthorities(
        User user,
        boolean isAdmin
    ) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Add role based on user type
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Add area-based authorities if needed
        if (user.getAreas() != null) {
            user
                .getAreas()
                .stream()
                .map(area ->
                    new SimpleGrantedAuthority(
                        "AREA_" + area.getName().toUpperCase()
                    )
                )
                .forEach(authorities::add);
        }

        return authorities;
    }

    // Spring Security required methods with default implementations
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
        return true;
    }
}
