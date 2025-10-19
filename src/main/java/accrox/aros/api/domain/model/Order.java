package accrox.aros.api.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;

import accrox.aros.api.domain.model.enums.OrderStatus;

public class Order {
    private Long id;

    private OrderStatus status;

    private LocalDateTime takedAt;

    private Table table;

    private Collection<ClientOrder> clientOrders;

    private Float total;

    public Order() {
    }

    public Order(
        Long id,
        OrderStatus status,
        LocalDateTime takedAt,
        Table table,
        Collection<ClientOrder> clientOrders,
        Float total
    ) {
        this.id = id;
        this.status = status;
        this.takedAt = takedAt;
        this.table = table;
        this.clientOrders = clientOrders;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getTakedAt() {
        return takedAt;
    }

    public void setTakedAt(LocalDateTime takedAt) {
        this.takedAt = takedAt;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Collection<ClientOrder> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(Collection<ClientOrder> clientOrders) {
        this.clientOrders = clientOrders;
    }

    public void addClientOrder(ClientOrder order) {
        this.clientOrders.add(order);
        order.setOrder(this);
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
