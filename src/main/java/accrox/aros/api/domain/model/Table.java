package accrox.aros.api.domain.model;

import java.util.Collection;

public class Table {
    private Long id;

    private String name;

    private Collection<Order> orders;

    public Table() {
    }

    public Table(Long id, String name, Collection<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }
}
