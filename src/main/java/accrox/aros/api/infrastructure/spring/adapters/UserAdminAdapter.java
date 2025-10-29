package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.infrastructure.spring.config.beans.AdminBeanConfig;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAdminAdapter implements AdminAuthService {

    @Autowired
    private AdminBeanConfig adminProperties;

    private static final Logger logger = LoggerFactory.getLogger(
        UserAdminAdapter.class
    );

    @Override
    public boolean isAdminCredentials(String document) {
        if (document == null) {
            return false;
        }
        if (!document.equalsIgnoreCase(adminProperties.getDocument())) {
            return false;
        }

        logger.info(
            "Validating admin credentials for: {}",
            adminProperties.getDocument()
        );

        return true;
    }

    @Override
    public User getUser() {
        String password = adminProperties.getPassword();

        if (password.startsWith("'") && password.endsWith("'")) {
            password = password.substring(1, password.length() - 1);
        }

        User user = new User();
        user.setDocument(adminProperties.getDocument());
        user.setEmail(adminProperties.getDocument());
        user.setPassword(adminProperties.getPassword());
        user.setAreas(List.of(new Area("ADMINISTRATION")));

        return user;
    }
}
