package accrox.aros.api.infrastructure.spring.security.authorization;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import accrox.aros.api.domain.service.PasswordService;

@Service
public class PasswordServiceAdapter implements PasswordService {
    private PasswordEncoder encoder;

    public PasswordServiceAdapter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String password) {
        return this.encoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encoded) {
        return this.encoder.matches(password, encoded);
    }
}
