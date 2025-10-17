package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.product.UpdateProductCategoriesUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateProductCategoriesUseCaseBeansConfig {
    @Bean
    public UpdateProductCategoriesUseCase updateProductCategoriesUseCase(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        return new UpdateProductCategoriesUseCase(productRepository,categoryRepository);
    }
}
