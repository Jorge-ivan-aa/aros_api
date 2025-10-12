package accrox.aros.api.infrastructure.spring.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "category_positions",
    uniqueConstraints = @UniqueConstraint(columnNames = { "id_daymenu", "id_category" })
)
public class CategoryPositionEntity {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_daymenu")
    private DaymenuEntity daymenu;

    @Column(columnDefinition = "TINYINT")
    private Short position;

    public CategoryPositionEntity() {
    }

    public CategoryPositionEntity(
        Long id,
        CategoryEntity category,
        DaymenuEntity daymenu,
        Short position
    ) {
        this.id = id;
        this.category = category;
        this.daymenu = daymenu;
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
}
