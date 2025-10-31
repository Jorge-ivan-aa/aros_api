package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.order.GetOrdersByStatusUseCase;
import accrox.aros.api.domain.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetOrdersByStatusUseCaseBeansConfig {
    @Bean
    public GetOrdersByStatusUseCase getOrdersByStatusUseCase(
            OrderRepository repository
    ) {
        return new GetOrdersByStatusUseCase(repository);
    }
}
