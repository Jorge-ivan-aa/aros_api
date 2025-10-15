package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Order;

public interface OrderRepository {
    public void create(Order order);
}