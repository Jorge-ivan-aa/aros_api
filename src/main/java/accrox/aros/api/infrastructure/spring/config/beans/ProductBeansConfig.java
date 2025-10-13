package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.domain.repository.ProductRepository;

@Configuration
public class ProductBeansConfig {
    @Bean
    public CreateProductUseCase createProductUseCase(
        ProductRepository repository
    ) {
        return new CreateProductUseCase(repository);
    }
}
