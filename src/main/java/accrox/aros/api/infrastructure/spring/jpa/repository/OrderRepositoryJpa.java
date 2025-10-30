package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.List;

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
}
