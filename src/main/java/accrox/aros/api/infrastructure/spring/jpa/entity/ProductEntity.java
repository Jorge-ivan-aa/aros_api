package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "products",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "preparation_area", nullable = true)
    private AreaEntity preparationArea;

    @Column(name = "preparation_time", columnDefinition = "TINYINT UNSIGNED")
    private Integer preparationTime;

    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_categories",
        joinColumns = @JoinColumn(name = "id_product"),
        inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private Collection<CategoryEntity> categories;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Collection<DaymenuEntity> parent;
    
    public ProductEntity() {
    }

    public ProductEntity(
        Long id,
        String name,
        String description,
        Float price,
        AreaEntity preparationArea,
        Integer preparationTime,
        Boolean active,
        Collection<CategoryEntity> categories
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationArea = preparationArea;
        this.preparationTime = preparationTime;
        this.active = active;
        this.categories = categories;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public AreaEntity getPreparationArea() {
        return preparationArea;
    }

    public void setPreparationArea(AreaEntity preparationArea) {
        this.preparationArea = preparationArea;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Collection<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Collection<CategoryEntity> categories) {
        this.categories = categories;
    }
}
