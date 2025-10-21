package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.category.CreateCategoryUseCase;
import accrox.aros.api.application.usecases.category.GetAllCategoriesUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryBeansConfig {

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(
        CategoryRepository repository
    ) {
        return new CreateCategoryUseCase(repository);
    }

    @Bean
    public GetAllCategoriesUseCase getAllCategoriesUseCase(
        CategoryRepository repository
    ) {
        return new GetAllCategoriesUseCase(repository);
    }
}
