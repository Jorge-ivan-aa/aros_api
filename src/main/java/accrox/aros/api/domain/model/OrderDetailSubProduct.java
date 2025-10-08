package accrox.aros.api.domain.model;

public class OrderDetailSubProduct {
    private Long id;

    private String name;

    private OrderDetail detail;

    public OrderDetailSubProduct() {
    }

    public OrderDetailSubProduct(Long id, String name, OrderDetail detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
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

    public OrderDetail getDetail() {
        return detail;
    }

    public void setDetail(OrderDetail detail) {
        this.detail = detail;
    }

    
}
