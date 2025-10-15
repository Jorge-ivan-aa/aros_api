package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;
import accrox.aros.api.domain.repository.OrderRepository;

@Configuration
public class OrderBeansConfig {

    @Bean
    public MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase(
        OrderRepository orderRepository
    ) {
        return new MarkOrderAsCompletedUseCase(orderRepository);
    }

}
