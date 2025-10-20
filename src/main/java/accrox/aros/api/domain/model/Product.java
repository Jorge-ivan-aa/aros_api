package accrox.aros.api.domain.model;

import java.util.Collection;

import io.jsonwebtoken.lang.Collections;

public class Product {
    private Long id;

    private String name;

    private String description;

    private Float price;

    private Area preparationArea;

    private Integer preparationTime;

    private Boolean active;

    private Collection<Category> categories;

    private Integer quantity;

    private String observations;

    public Product() {
    }

    public Product(
            Long id,
            String name,
            String description,
            Float price,
            Area preparationArea,
            Integer preparationTime,
            Boolean active,
            Collection<Category> categories,
            Integer quantity,
            String observations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationArea = preparationArea;
        this.preparationTime = preparationTime;
        this.active = active;
        this.categories = categories;
        this.quantity = quantity;
        this.observations = observations;
    }

    public Product(
            Long id,
            String name,
            String description,
            Float price,
            Integer quantity) {
        this(
                id,
                name,
                description,
                price,
                null,
                quantity,
                true,
                Collections.emptyList(),
                quantity,
                description);
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

    public Area getPreparationArea() {
        return preparationArea;
    }

    public void setPreparationArea(Area preparationArea) {
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

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Float calculateTotal() {
        return this.price * this.quantity;
    }
}
