package accrox.aros.api.infrastructure.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import accrox.aros.api.domain.service.PasswordHasher;

@Service
public class BCryptPasswordHasher implements PasswordHasher {
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
