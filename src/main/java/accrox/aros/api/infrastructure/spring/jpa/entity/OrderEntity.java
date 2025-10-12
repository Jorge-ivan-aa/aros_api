package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.time.LocalDateTime;
import java.util.Set;

import accrox.aros.api.domain.model.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private Long id;

    private OrderStatus status;

    @Column(name = "taked_at")
    private LocalDateTime takedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_table")
    private TableEntity table;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private Set<ClientOrderEntity> orders;

    private Float total;

    public OrderEntity() {
    }

    public OrderEntity(
        Long id,
        OrderStatus status,
        LocalDateTime takedAt,
        TableEntity table,
        Set<ClientOrderEntity> orders,
        Float total
    ) {
        this.id = id;
        this.status = status;
        this.takedAt = takedAt;
        this.table = table;
        this.orders = orders;
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

    public TableEntity getTable() {
        return table;
    }

    public void setTable(TableEntity table) {
        this.table = table;
    }

    public Set<ClientOrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<ClientOrderEntity> orders) {
        this.orders = orders;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
