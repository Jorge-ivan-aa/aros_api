package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.daymenu.GetAllDayMenusUseCase;
import accrox.aros.api.application.usecases.product.CreateDayMenuUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DayMenuBeansConfig {

    @Bean
    public CreateDayMenuUseCase createDayMenuUseCase(
        DaymenuRepository repository,
        ProductRepository productRepository,
        CategoryRepository categoryRepository
    ) {
        return new CreateDayMenuUseCase(
            repository,
            categoryRepository,
            productRepository
        );
    }

    @Bean
    public GetAllDayMenusUseCase getAllDayMenusUseCase(
        DaymenuRepository repository
    ) {
        return new GetAllDayMenusUseCase(repository);
    }
}
