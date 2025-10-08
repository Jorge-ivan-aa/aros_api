package accrox.aros.api.domain.model;

import java.util.Collection;

import accrox.aros.api.domain.model.enums.OrderStatus;

public class ClientOrder {
    private Long id;

    private Order order;

    private OrderStatus status;

    private Collection<OrderDetail> details;

    public ClientOrder(Long id, Order order, OrderStatus status, Collection<OrderDetail> details) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Collection<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(Collection<OrderDetail> details) {
        this.details = details;
    }
}
