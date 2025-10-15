package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.util.Collection;

import accrox.aros.api.domain.model.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_orders")
public class ClientOrderEntity {
    @Id
    private Long id;

    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    private Float total;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private Collection<ClientOrderDetailEntity> details;

    public ClientOrderEntity() {
    }

    public ClientOrderEntity(
        Long id,
        OrderStatus status,
        OrderEntity order,
        Float total,
        Collection<ClientOrderDetailEntity> details
    ) {
        this.id = id;
        this.status = status;
        this.order = order;
        this.total = total;
        this.details = details;
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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Collection<ClientOrderDetailEntity> getDetails() {
        return details;
    }

    public void setDetails(Collection<ClientOrderDetailEntity> details) {
        this.details = details;
    }
}
