package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Order;

import java.util.List;

public interface OrderRepository {

    public void MarkOrderAsCompleted(Long orderId);

    public void create(Order order);

    public List<Order> findAll();
}