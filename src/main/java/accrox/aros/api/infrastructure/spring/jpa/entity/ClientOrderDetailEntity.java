package accrox.aros.api.infrastructure.spring.jpa.entity;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "order_details"
)
public class ClientOrderDetailEntity {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private ClientOrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    private String name;

    private Float price;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer quantity;

    private String observations;

    @OneToMany(mappedBy = "detail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<OrderDetailSubProductEntity> subProducts;

    public ClientOrderDetailEntity() {
    }

    public ClientOrderDetailEntity(
        Long id,
        ClientOrderEntity order,
        ProductEntity product,
        String name,
        Float price,
        Integer quantity,
        String observations,
        Collection<OrderDetailSubProductEntity> subProducts
    ) {
        this.id = id;
        this.order = order;
        this.product = product;
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

    public ClientOrderEntity getOrder() {
        return order;
    }

    public void setOrder(ClientOrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
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

    public Collection<OrderDetailSubProductEntity> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(Collection<OrderDetailSubProductEntity> subProducts) {
        this.subProducts = subProducts;
    }
}
