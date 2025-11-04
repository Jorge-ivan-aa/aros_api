package accrox.aros.api.application.dto.daymenu;

public class DaymenuProductOutput {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer preparationTime;

    public DaymenuProductOutput() {
    }

    public DaymenuProductOutput(Long id, String name, String description, Float price, Integer preparationTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
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
}
