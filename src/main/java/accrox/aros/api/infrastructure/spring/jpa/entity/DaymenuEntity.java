package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "daymenus")
@PrimaryKeyJoinColumn(name = "id")
public class DaymenuEntity extends ProductEntity {
    private LocalDate creation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "daymenu", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<DayMenuCategoryEntity> products;

    public DaymenuEntity() {
        this.products = new LinkedList<>();
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
        Collection<DayMenuCategoryEntity> products,
        LocalDate creation
    ) {
        super(id, name, description, price, preparationArea, preparationTime, active, categories);
        this.creation = creation;
        this.products = products;
    }

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }

    public Collection<DayMenuCategoryEntity> getProducts() {
        return products;
    }

    public void setProducts(Collection<DayMenuCategoryEntity> products) {
        this.products = products;
    }

    public void addProduct(DayMenuCategoryEntity product) {
        this.products.add(product);
        product.setDaymenu(this);
    }
}
