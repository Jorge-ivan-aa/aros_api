package accrox.aros.api.infrastructure.spring.config.beans;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import accrox.aros.api.application.exceptions.auth.InsecurePasswordException;
import jakarta.annotation.PostConstruct;

@Component
public class AdminBeanConfig {

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[abxy]\\$\\d{2}\\$.{53}$");

    @PostConstruct
    public void validatePasswordSecurity() {

        if (password.isEmpty()) {
            throw new InsecurePasswordException();
        }

        if (password.startsWith("'") && password.endsWith("'")) {
            password = password.substring(1, password.length() - 1);
        }

        if (!isPasswordHashed(password)) {
            String hashedPassword = encoder.encode(password);
            throw new InsecurePasswordException(email, hashedPassword);
        }
    }

    private boolean isPasswordHashed(String password) {
        return password != null && BCRYPT_PATTERN.matcher(password).matches();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
