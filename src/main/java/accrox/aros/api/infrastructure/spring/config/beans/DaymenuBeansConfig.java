package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.daymenu.CreateDayMenuUseCase;
import accrox.aros.api.application.usecases.daymenu.GetCurrentDaymenuUseCase;
import accrox.aros.api.application.usecases.daymenu.GetDaymenuByDateUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaymenuBeansConfig {

    @Bean
    public CreateDayMenuUseCase createDayMenuUseCase(
        DaymenuRepository daymenuRepository,
        CategoryRepository categoryRepository,
        ProductRepository productRepository
    ) {
        return new CreateDayMenuUseCase(daymenuRepository, categoryRepository, productRepository);
    }

    @Bean
    public GetCurrentDaymenuUseCase getCurrentDaymenuUseCase(
        DaymenuRepository daymenuRepository
    ) {
        return new GetCurrentDaymenuUseCase(daymenuRepository);
    }

    @Bean
    public GetDaymenuByDateUseCase getDaymenuByDateUseCase(
        DaymenuRepository daymenuRepository
    ) {
        return new GetDaymenuByDateUseCase(daymenuRepository);
    }
}
