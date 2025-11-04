package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.daymenu.GetCurrentDaymenuUseCase;
import accrox.aros.api.application.usecases.daymenu.GetDaymenuByDateUseCase;
import accrox.aros.api.domain.repository.DaymenuRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaymenuBeansConfig {

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
