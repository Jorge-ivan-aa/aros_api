package accrox.aros.api.domain.model;

import java.util.Collection;

import accrox.aros.api.domain.model.enums.OrderStatus;

public class ClientOrder {
    private Long id;

    private Order order;

    private OrderStatus status;

    private Collection<Product> details;

    private Float total;

    public ClientOrder() {
    }

    public ClientOrder(Long id, Order order, OrderStatus status, Collection<Product> details, Float total) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.details = details;
        this.total = total;
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

    public Collection<Product> getDetails() {
        return details;
    }

    public void setDetails(Collection<Product> details) {
        this.details = details;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
