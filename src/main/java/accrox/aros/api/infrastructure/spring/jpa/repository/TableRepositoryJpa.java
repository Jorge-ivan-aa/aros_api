package accrox.aros.api.infrastructure.spring.jpa.repository;

import accrox.aros.api.infrastructure.spring.jpa.entity.TableEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TableRepositoryJpa extends CrudRepository<TableEntity, Long> {
    public boolean existsByName(String name);

    @Query("SELECT DISTINCT t FROM TableEntity t LEFT JOIN FETCH t.orders")
    List<TableEntity> findAllWithOrders();
}
