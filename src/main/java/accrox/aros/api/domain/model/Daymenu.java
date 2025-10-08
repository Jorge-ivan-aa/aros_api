package accrox.aros.api.domain.model;

import java.time.LocalDate;
import java.util.Collection;

public class Daymenu extends Product {
    private LocalDate creation;

    private Collection<Product> subproducts;

    public Daymenu() {
    }

    public Daymenu(
        Long id,
        String name,
        String description,
        Float price,
        Area preparationArea,
        Integer preparationTime,
        Boolean active,
        Collection<Category> categories,
        LocalDate creation,
        Collection<Product> subproducts
    ) {
        super(id, name, description, price, preparationArea, preparationTime, active, categories);
        this.creation = creation;
        this.subproducts = subproducts;
    }

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }

    public Collection<Product> getSubproducts() {
        return subproducts;
    }

    public void setSubproducts(Collection<Product> subproducts) {
        this.subproducts = subproducts;
    }
}
