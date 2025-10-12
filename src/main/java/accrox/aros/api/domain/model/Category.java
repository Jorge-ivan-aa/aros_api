package accrox.aros.api.domain.model;

import java.util.Collection;

public class Category {
    private Long id;

    private String name;

    private Collection<Product> products;

    public Category() {
    }

    public Category(Long id, String name, Collection<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
