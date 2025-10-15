package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.OrderRepositoryJpa;

@Repository
public class OrderJpaAdapter implements OrderRepository {

    @Autowired
    private OrderRepositoryJpa orderRepositoryJpa;

    @Override
    public void MarkOrderAsCompleted(Long orderId) {
        OrderEntity orderEntity = this.orderRepositoryJpa.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderEntity.setStatus(OrderStatus.COMPLETED);
        this.orderRepositoryJpa.save(orderEntity);
    }

}
