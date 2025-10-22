package accrox.aros.api.infrastructure.spring.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Path http://localhost:8080/swagger-ui.html
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Accrox Restaurant Ordering System API")
                    .version("1.0")
                    .description(
                        "API documentation for the Accrox Restaurant Ordering System"
                    )
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(
                new io.swagger.v3.oas.models.Components().addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description(
                            "Enter your JWT token in the format: Bearer <token>"
                        )
                )
            );
    }
}
