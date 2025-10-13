package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.category.CreateCategoryUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;

@Configuration
public class CategoryBeansConfig {
    @Bean
    public CreateCategoryUseCase createCategoryUseCase(
        CategoryRepository repository
    ) {
        return new CreateCategoryUseCase(repository);
    }
}
