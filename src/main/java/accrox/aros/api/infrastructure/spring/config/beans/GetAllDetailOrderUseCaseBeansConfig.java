package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.order.GetAllDetailOrderUseCase;
import accrox.aros.api.domain.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAllDetailOrderUseCaseBeansConfig {

    @Bean
    public GetAllDetailOrderUseCase getAllDetailOrderUseCase(OrderRepository orderRepository) {
        return new GetAllDetailOrderUseCase(orderRepository);
    }
}
