package accrox.aros.api.infrastructure.spring.adapters;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.infrastructure.spring.config.beans.AdminBeanConfig;

@Component
public class UserAdminAdapter implements AdminAuthService {

    @Autowired
    private AdminBeanConfig adminProperties;
    private static final Logger logger = LoggerFactory.getLogger(UserAdminAdapter.class);

    @Override
    public boolean isAdminCredentials(String email) {

        if (email == null) {
            return false;
        }
        if (!email.equalsIgnoreCase(adminProperties.getEmail())) {
            return false;
        }

        logger.info("Validating admin credentials for: {}", adminProperties.getEmail());

        return true;
    }

    @Override
    public User getUser() {

        String password = adminProperties.getPassword();

        if (password.startsWith("'") && password.endsWith("'")) {
            password = password.substring(1, password.length() - 1);
        }

        User user = new User(adminProperties.getEmail(), adminProperties.getPassword());
        user.setAreas(List.of(new Area("ADMINISTRATION")));
    
        return user;
    }

}