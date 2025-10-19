package accrox.aros.api.infrastructure.spring.config.beans.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;


//Path http://localhost:8080/swagger-ui.html
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Accrox Restaurant Ordering System API")
                        .version("1.0")
                        .description("API documentation for the Accrox Restaurant Ordering System"));
    }
}
