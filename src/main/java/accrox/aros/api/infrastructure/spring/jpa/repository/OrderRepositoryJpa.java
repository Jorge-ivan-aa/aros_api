package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import accrox.aros.api.domain.model.enums.OrderStatus;
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

    @Query("""
                SELECT d.product.id, SUM(d.quantity)
                FROM OrderEntity o
                JOIN o.orders co
                JOIN co.details d
                WHERE o.status = :status AND d.product.id IS NOT NULL
                GROUP BY d.product.id
                ORDER BY SUM(d.quantity) DESC
            """)
    List<Object[]> findSoldProductQuantities(OrderStatus status);

    @Query("""
            SELECT o
            FROM OrderEntity o
            WHERE o.status NOT IN ('COMPLETED', 'CANCELLED')
              AND o.takedAt < :today
            """)
    List<OrderEntity> findUncompletedEntitiesBefore(@Param("today") LocalDateTime today);
}
