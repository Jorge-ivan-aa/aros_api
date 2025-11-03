package accrox.aros.api.application.dto.daymenu;

import java.time.LocalDate;
import java.util.List;

public class DaymenuOutput {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer preparationTime;
    private Boolean active;
    private LocalDate creation;
    private List<DaymenuCategoryOutput> categories;

    public DaymenuOutput() {
    }

    public DaymenuOutput(Long id, String name, String description, Float price, Integer preparationTime, Boolean active, LocalDate creation, List<DaymenuCategoryOutput> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
        this.active = active;
        this.creation = creation;
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

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }

    public List<DaymenuCategoryOutput> getCategories() {
        return categories;
    }

    public void setCategories(List<DaymenuCategoryOutput> categories) {
        this.categories = categories;
    }
}
