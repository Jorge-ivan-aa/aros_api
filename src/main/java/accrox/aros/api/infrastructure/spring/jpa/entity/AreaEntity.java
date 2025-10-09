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
    name = "areas",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class AreaEntity {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "preparationArea", fetch = FetchType.LAZY)
    private Collection<ProductEntity> products;

    public AreaEntity() {
    }

    public AreaEntity(Long id, String name, Collection<ProductEntity> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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

    public Collection<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductEntity> products) {
        this.products = products;
    }
}
