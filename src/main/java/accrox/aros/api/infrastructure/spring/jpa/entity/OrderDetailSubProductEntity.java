package accrox.aros.api.infrastructure.spring.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_detail_subproducts")
public class OrderDetailSubProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detail")
    private ClientOrderDetailEntity detail;

    private String name;

    private String observations;

    public OrderDetailSubProductEntity() {
    }

    public OrderDetailSubProductEntity(
        Long id,
        ClientOrderDetailEntity detail,
        String name,
        String observations
    ) {
        this.id = id;
        this.detail = detail;
        this.name = name;
        this.observations = observations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientOrderDetailEntity getDetail() {
        return detail;
    }

    public void setDetail(ClientOrderDetailEntity detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}