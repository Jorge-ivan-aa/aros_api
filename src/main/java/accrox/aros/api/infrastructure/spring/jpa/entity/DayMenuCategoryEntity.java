package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "daymenu_categories",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_daymenu", "id_category" }),
        @UniqueConstraint(columnNames = { "id_daymenu", "position" })
    }
)
public class DayMenuCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_daymenu")
    private DaymenuEntity daymenu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    private CategoryEntity category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "daymenu_products",
        joinColumns = @JoinColumn(name = "id_daymenu_category"),
        inverseJoinColumns = @JoinColumn(name = "id_subproduct"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = { "id_daymenu_category", "id_subproduct" })
        }
    )
    private Collection<ProductEntity> products;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Short position;

    public DayMenuCategoryEntity() {
        this.products = new LinkedList<>();
    }

    public DayMenuCategoryEntity(
        Long id,
        DaymenuEntity daymenu,
        CategoryEntity category,
        Collection<ProductEntity> products,
        Short position
    ) {
        this.id = id;
        this.daymenu = daymenu;
        this.category = category;
        this.products = products;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Collection<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductEntity> products) {
        this.products = products;
    }

    public DaymenuEntity getDaymenu() {
        return daymenu;
    }

    public void setDaymenu(DaymenuEntity daymenu) {
        this.daymenu = daymenu;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public void addProduct(ProductEntity product) {
        this.products.add(product);
    }
}
