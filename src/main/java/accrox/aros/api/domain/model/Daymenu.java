package accrox.aros.api.domain.model;

import java.time.LocalDate;
import java.util.Collection;

public class Daymenu extends Product {
    private LocalDate creation;

    private Collection<Product> subProducts;

    public Daymenu() {
    }

    public Daymenu(
        Long id,
        String name,
        String description,
        Float price,
        Integer quantity,
        LocalDate creation,
        Collection<Product> subProducts
    ) {
        super(id, name, description, price, quantity);
        this.creation = creation;
        this.subProducts = subProducts;
    }

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }

    public Collection<Product> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(Collection<Product> subProducts) {
        this.subProducts = subProducts;
    }
}
