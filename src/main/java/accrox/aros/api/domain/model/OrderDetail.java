package accrox.aros.api.domain.model;

import java.util.Collection;

public class OrderDetail {
    private Long id;

    private Product product;

    private Order order;

    private String name;

    private Float price;

    private Integer quantity;

    private String observations;

    private Collection<OrderDetailSubProduct> subProducts;

    public OrderDetail() {
    }

    public OrderDetail(
        Long id,
        Product product,
        Order order,
        String name,
        Float price,
        Integer quantity,
        String observations,
        Collection<OrderDetailSubProduct> subProducts
    ) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.observations = observations;
        this.subProducts = subProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Collection<OrderDetailSubProduct> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(Collection<OrderDetailSubProduct> subProducts) {
        this.subProducts = subProducts;
    }
}
