package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.List;

import accrox.aros.api.domain.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;

public interface OrderRepositoryJpa extends CrudRepository<OrderEntity, Long> {
    /**
     * find orders using his "responsible" field
     *
     * @param id responsible's id
     *
     * @return orders of that responsible
     */
    public List<OrderEntity> findAllByResponsibleId(Long id);

    @Query("""
        SELECT DISTINCT o FROM OrderEntity o
        LEFT JOIN FETCH o.table t
        LEFT JOIN FETCH o.responsible r
        LEFT JOIN FETCH o.orders co
        LEFT JOIN FETCH co.details d
        LEFT JOIN FETCH d.product p
    """)
    List<OrderEntity> findAllWithDetails();

}
