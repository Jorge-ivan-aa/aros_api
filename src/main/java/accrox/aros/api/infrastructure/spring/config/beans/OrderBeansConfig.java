package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.order.CancelOrderUseCase;
import accrox.aros.api.application.usecases.order.CancelOverdueOrdersUseCase;
import accrox.aros.api.application.usecases.order.CreateOrderUseCase;
import accrox.aros.api.application.usecases.order.GetAllDetailOrderUseCase;
import accrox.aros.api.application.usecases.order.GetCurrentDayOrdersUseCase;
import accrox.aros.api.application.usecases.order.GetOrdersByResponsibleUseCase;
import accrox.aros.api.application.usecases.order.GetOrdersByStatusUseCase;
import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;
import accrox.aros.api.application.usecases.order.UpdateOrderUseCase;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.domain.repository.TableRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.LoggerService;

@Configuration
public class OrderBeansConfig {
    @Bean
    public MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase(
            OrderRepository orderRepository) {
        return new MarkOrderAsCompletedUseCase(orderRepository);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            DaymenuRepository daymenuRepository,
            TableRepository tableRepository) {
        return new CreateOrderUseCase(
                orderRepository,
                productRepository,
                daymenuRepository,
                tableRepository);
    }

    @Bean
    public GetOrdersByResponsibleUseCase getOrdersByResponsibleUseCase(
            OrderRepository orderRepository) {
        return new GetOrdersByResponsibleUseCase(orderRepository);
    }

    @Bean
    public UpdateOrderUseCase updateOrderUseCase(
            OrderRepository orderRepository,
            TableRepository tableRepository,
            UserRepository userRepository) {
        return new UpdateOrderUseCase(
                orderRepository,
                tableRepository,
                userRepository);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(
            OrderRepository orderRepository) {
        return new CancelOrderUseCase(orderRepository);
    }

    @Bean
    public GetOrdersByStatusUseCase getOrdersByStatusUseCase(
            OrderRepository repository) {
        return new GetOrdersByStatusUseCase(repository);
    }

    @Bean
    public GetAllDetailOrderUseCase getAllDetailOrderUseCase(OrderRepository orderRepository) {
        return new GetAllDetailOrderUseCase(orderRepository);
    }

    @Bean
    public GetCurrentDayOrdersUseCase getCurrentDayOrdersUseCase(
            OrderRepository repository) {
        return new GetCurrentDayOrdersUseCase(repository);
    }

    @Bean
    public CancelOverdueOrdersUseCase cancelOverdueOrdersUseCase(OrderRepository orderRepository, LoggerService loggerService) {
        return new CancelOverdueOrdersUseCase(orderRepository, loggerService);
    }
}
