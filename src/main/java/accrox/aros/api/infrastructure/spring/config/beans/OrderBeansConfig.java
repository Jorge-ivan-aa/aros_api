package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.order.CreateOrderUseCase;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.domain.repository.TableRepository;

@Configuration
public class OrderBeansConfig {
    @Bean
    public CreateOrderUseCase createOrderUseCase(
        OrderRepository orderRepository,
        ProductRepository productRepository,
        DaymenuRepository daymenuRepository,
        TableRepository tableRepository
    ) {
        return new CreateOrderUseCase(
            orderRepository,
            productRepository,
            daymenuRepository,
            tableRepository
        );
    }
}
