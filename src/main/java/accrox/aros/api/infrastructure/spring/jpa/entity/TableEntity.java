package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "`tables`",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class TableEntity {
    @Id
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "table")
    private Collection<OrderEntity> orders;

    public TableEntity() {
    }

    public TableEntity(Long id, String name, Collection<OrderEntity> orders) {
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

    public Collection<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = orders;
    }
}
