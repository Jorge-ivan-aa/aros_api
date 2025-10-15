package accrox.aros.api.domain.model;

import java.util.Collection;

public class DayMenuSelection extends Product {
    private Collection<Product> selection;

    public DayMenuSelection() {
    }

    /**
     * @param id daymenu's id
     * @param name daymenu name
     * @param description
     * @param price
     * @param quantity
     * @param selection
     */
    public DayMenuSelection(
        Long id,
        String name,
        String description,
        Float price,
        Integer quantity,
        Collection<Product> selection
    ) {
        super(id, name, description, price, quantity);
        this.selection = selection;
    }

    public Collection<Product> getSelection() {
        return selection;
    }

    public void setSelection(Collection<Product> selection) {
        this.selection = selection;
    }
}
