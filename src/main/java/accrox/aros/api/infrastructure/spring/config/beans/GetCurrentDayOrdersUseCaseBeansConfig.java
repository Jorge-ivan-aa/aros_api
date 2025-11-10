package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.order.GetCurrentDayOrdersUseCase;

import accrox.aros.api.domain.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetCurrentDayOrdersUseCaseBeansConfig {
    @Bean
    public GetCurrentDayOrdersUseCase getCurrentDayOrdersUseCase(
            OrderRepository repository
    ) {
        return new GetCurrentDayOrdersUseCase(repository);
    }
}
