package accrox.aros.api.application.usecases.order;

import accrox.aros.api.application.exceptions.order.OrderNotFoundException;
import accrox.aros.api.domain.repository.OrderRepository;

public class CancelOrderUseCase {
    private final OrderRepository orderRepository;

    public CancelOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(Long orderId) throws OrderNotFoundException {
        this.orderRepository.cancelOrder(orderId);
    }
}
