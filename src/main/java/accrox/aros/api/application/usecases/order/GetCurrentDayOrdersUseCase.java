package accrox.aros.api.application.usecases.order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import accrox.aros.api.application.dto.order.OrdersOutput;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.repository.OrderRepository;

public class GetCurrentDayOrdersUseCase {

        private OrderRepository orderRepository;

        public GetCurrentDayOrdersUseCase(OrderRepository orderRepository) {
                this.orderRepository = orderRepository;
        }

        public List<OrdersOutput> execute() {

                List<Order> allOrders = orderRepository.findAll();

                LocalDate today = LocalDate.now();

                List<Order> todayOrders = allOrders.stream()
                                .filter(order -> order.getTakedAt() != null &&
                                                order.getTakedAt().toLocalDate().isEqual(today))
                                .toList();

                return todayOrders.stream()
                                .map(order -> new OrdersOutput(
                                                order.getId().intValue(),
                                                order.getStatus().name(),
                                                order.getTakedAt() != null ? order.getTakedAt().toString() : null,
                                                order.getTable() != null ? order.getTable().getName() : null,
                                                order.getTotal()))
                                .collect(Collectors.toList());
        }
}
