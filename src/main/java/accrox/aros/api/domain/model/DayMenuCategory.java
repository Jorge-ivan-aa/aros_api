package accrox.aros.api.domain.model;

import java.util.Collection;

public class DayMenuCategory {
    private Long id;

    private Daymenu daymenu;

    private Category category;

    private Collection<Product> products;

    private Short position;

    public DayMenuCategory() {
    }

    public DayMenuCategory(
        Long id,
        Daymenu daymenu,
        Category category,
        Collection<Product> products,
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public Daymenu getDaymenu() {
        return daymenu;
    }

    public void setDaymenu(Daymenu daymenu) {
        this.daymenu = daymenu;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }
}
