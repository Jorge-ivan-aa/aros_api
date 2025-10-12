package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "daymenus")
@PrimaryKeyJoinColumn(name = "id")
public class DaymenuEntity extends ProductEntity {
    private LocalDate creation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "subproducts",
        joinColumns = @JoinColumn(name = "id_daymenu"),
        inverseJoinColumns = @JoinColumn(name = "id_subproduct")
    )
    private Collection<ProductEntity> products;

    @OneToMany(mappedBy = "daymenu", fetch = FetchType.LAZY)
    private Collection<CategoryPositionEntity> positions;

    public DaymenuEntity() {
    }

    public DaymenuEntity(
        Long id,
        String name,
        String description,
        Float price,
        AreaEntity preparationArea,
        Integer preparationTime,
        Boolean active,
        Collection<CategoryEntity> categories,
        Collection<ProductEntity> products,
        Collection<CategoryPositionEntity> positions,
        LocalDate creation
    ) {
        super(id, name, description, price, preparationArea, preparationTime, active, categories);
        this.creation = creation;
        this.products = products;
        this.positions = positions;
    }

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }

    public Collection<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductEntity> products) {
        this.products = products;
    }

    public Collection<CategoryPositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(Collection<CategoryPositionEntity> positions) {
        this.positions = positions;
    }
}
