package accrox.aros.api.domain.model;

import java.time.LocalDate;
import java.util.Collection;

public class Daymenu extends Product {
    private LocalDate creation;

    private Collection<DayMenuCategory> subProducts;

    public Daymenu() {
    }

    public Daymenu(
        Long id,
        String name,
        String description,
        Float price,
        Integer quantity,
        LocalDate creation,
        Collection<DayMenuCategory> subProducts
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

    public Collection<DayMenuCategory> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(Collection<DayMenuCategory> subProducts) {
        this.subProducts = subProducts;
    }
}
