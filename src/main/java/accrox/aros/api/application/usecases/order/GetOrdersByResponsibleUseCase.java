package accrox.aros.api.application.usecases.order;

import java.util.List;

import accrox.aros.api.application.dto.order.OrdersOutput;
import accrox.aros.api.domain.repository.OrderRepository;

public class GetOrdersByResponsibleUseCase {
    private OrderRepository orderRepository;
    
    public GetOrdersByResponsibleUseCase(OrderRepository repository) {
        this.orderRepository = repository;
    }
    
    public List<OrdersOutput> execute(Long responsibleId) {
        return this.orderRepository.findAllByResponsible(responsibleId)
            .stream()
            .map(o -> {
                return new OrdersOutput(
                    o.getId().intValue(),
                    o.getStatus().name(),
                    o.getTakedAt().toString(),
                    o.getTable().getName(),
                    o.getTotal()
                );
            })
            .toList();
    }
}
