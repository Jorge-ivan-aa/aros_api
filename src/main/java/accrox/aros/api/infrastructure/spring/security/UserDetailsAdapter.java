package accrox.aros.api.infrastructure.spring.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;

public class UserDetailsAdapter implements UserDetails {
    private User data;

    public UserDetailsAdapter(User user) {
        this.data = user;
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
        return null;
    }

    public User getData() {
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
}
