package accrox.aros.api.application.dto.daymenu;

import java.util.List;

public class DaymenuCategoryOutput {
    private Long id;
    private String name;
    private List<DaymenuProductOutput> products;
    private Short position;

    public DaymenuCategoryOutput() {
    }

    public DaymenuCategoryOutput(Long id, String name, List<DaymenuProductOutput> products, Short position) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.position = position;
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

    public List<DaymenuProductOutput> getProducts() {
        return products;
    }

    public void setProducts(List<DaymenuProductOutput> products) {
        this.products = products;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }
}
