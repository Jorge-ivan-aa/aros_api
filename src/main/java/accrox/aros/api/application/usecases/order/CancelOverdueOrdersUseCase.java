package accrox.aros.api.application.usecases.order;

import java.time.LocalDateTime;
import java.util.List;

import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.service.LoggerService;

public class CancelOverdueOrdersUseCase {

    private final OrderRepository orderRepository;
    private final LoggerService loggerService;

    public CancelOverdueOrdersUseCase(OrderRepository orderRepository, LoggerService loggerService) {
        this.orderRepository = orderRepository;
        this.loggerService = loggerService;
    }

    public void execute() {
        LocalDateTime today = LocalDateTime.now();

        List<Order> overdueOrders = orderRepository.findUncompletedOrdersBefore(today);
        overdueOrders.forEach(order -> order.setStatus(OrderStatus.CANCELLED));

        if (!overdueOrders.isEmpty()) {
            orderRepository.saveAll(overdueOrders);
            loggerService.warn(overdueOrders.size() + " expired orders were cancelled.");
        } else {
            loggerService.info("There are no overdue orders to cancel.");
        }
    }
}
