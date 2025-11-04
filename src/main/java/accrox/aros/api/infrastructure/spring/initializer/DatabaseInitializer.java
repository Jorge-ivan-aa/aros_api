package accrox.aros.api.infrastructure.spring.initializer;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(
            DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            if (isDatabaseEmpty()) {
                logger.info("The database is empty. Loading initial data...");

                ClassPathResource resource = new ClassPathResource(
                        "data/initial-data.sql");

                DataSource dataSource = jdbcTemplate.getDataSource();
                if (dataSource != null) {
                    try (Connection connection = dataSource.getConnection()) {
                        ScriptUtils.executeSqlScript(connection, resource);
                    }
                } else {
                    logger.error("DataSource is null, cannot initialize database");
                    return;
                }

                logger.info("Initial data loaded successfully.");
            } else {
                logger.info(
                        "The database already contains data. Initial load will not be performed.");
            }
        } catch (Exception e) {
            logger.error(
                    "Error during database initialization: {}",
                    e.getMessage(),
                    e);
        }
    }

    private boolean isDatabaseEmpty() {
        try {
            Integer userCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM users",
                    Integer.class);
            Integer areaCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM areas",
                    Integer.class);
            return (userCount == null || userCount == 0) && (areaCount == null || areaCount == 0);
        } catch (Exception e) {
            logger.warn("Error checking database status: {}", e.getMessage());
            return true; // Assume database is empty if we can't check
        }
    }
}
