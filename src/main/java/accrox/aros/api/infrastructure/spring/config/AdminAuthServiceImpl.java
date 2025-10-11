package accrox.aros.api.infrastructure.spring.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.infrastructure.spring.config.beans.AdminBeanConfig;

@Component
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminBeanConfig adminProperties;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AdminAuthServiceImpl(AdminBeanConfig adminProperties) {
        this.adminProperties = adminProperties;
    }

    @Override
    public boolean isAdminCredentials(String email, String password) {

        if (!email.equals(adminProperties.getEmail())) {
            return false;
        }
        return encoder.matches(password, adminProperties.getPassword());
    }
}