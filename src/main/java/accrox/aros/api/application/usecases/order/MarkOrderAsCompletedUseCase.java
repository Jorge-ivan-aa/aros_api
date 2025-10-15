package accrox.aros.api.application.usecases.order;

import accrox.aros.api.domain.repository.OrderRepository;

public class MarkOrderAsCompletedUseCase {
    private OrderRepository orderRepository;

    public MarkOrderAsCompletedUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public void execute(Long orderId) {
        this.orderRepository.MarkOrderAsCompleted(orderId);
    }
}
