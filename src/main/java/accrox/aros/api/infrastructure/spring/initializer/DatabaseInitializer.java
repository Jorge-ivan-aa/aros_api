package accrox.aros.api.infrastructure.spring.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            if (isDatabaseEmpty()) {
                System.out.println("✅ The database is empty. Loading initial data...");

                ClassPathResource resource = new ClassPathResource("data/initial-data.sql");

                try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
                    ScriptUtils.executeSqlScript(connection, resource);
                }

                System.out.println("🎉 Initial data loaded successfully.");
            } else {
                System.out.println("ℹ️ The database already contains data. Initial load will not be performed.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error during database initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isDatabaseEmpty() {
        int userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        int areaCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM areas", Integer.class);
        return userCount == 0 && areaCount == 0;
    }
}