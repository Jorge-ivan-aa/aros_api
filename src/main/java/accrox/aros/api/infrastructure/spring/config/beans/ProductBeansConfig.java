package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.application.usecases.product.GetAllProductsUseCase;
import accrox.aros.api.application.usecases.product.UpdateProductUseCase;
import accrox.aros.api.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeansConfig {

    @Bean
    public CreateProductUseCase createProductUseCase(
        ProductRepository repository
    ) {
        return new CreateProductUseCase(repository);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(
        ProductRepository repository
    ) {
        return new UpdateProductUseCase(repository);
    }

    @Bean
    public GetAllProductsUseCase getAllProductsUseCase(
        ProductRepository repository
    ) {
        return new GetAllProductsUseCase(repository);
    }
}
