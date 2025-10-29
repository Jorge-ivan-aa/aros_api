package accrox.aros.api.application.usecases.order;

import accrox.aros.api.application.dto.order.OrdersOutput;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetOrdersByStatusUseCase {

    private OrderRepository orderRepository;

    public GetOrdersByStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrdersOutput> execute(String status) {

        List<Order> allOrders = orderRepository.findAll();

        OrderStatus orderStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                orderStatus = OrderStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Si el valor no coincide, dejamos orderStatus en null
                System.out.println("Invalid status: " + status + ". All orders will be returned.");
            }
        }

        List<Order> filteredOrders;

        if (orderStatus != null) {
            OrderStatus finalOrderStatus = orderStatus;
            OrderStatus finalOrderStatus1 = orderStatus;
            filteredOrders = allOrders.stream()
                    .filter(order -> order.getStatus() == finalOrderStatus1)
                    .collect(Collectors.toList());
        } else {
            filteredOrders = allOrders;
        }


        return filteredOrders.stream()
                .map(order -> new OrdersOutput(
                        order.getId().intValue(),
                        order.getStatus().name(),
                        order.getTakedAt() != null ? order.getTakedAt().toString() : null,
                        order.getTable() != null ? order.getTable().getName() : null,
                        order.getTotal()
                ))
                .collect(Collectors.toList());
    }
}
