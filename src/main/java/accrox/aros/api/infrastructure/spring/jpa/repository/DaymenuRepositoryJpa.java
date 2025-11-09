package accrox.aros.api.infrastructure.spring.jpa.repository;

import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DaymenuRepositoryJpa
    extends CrudRepository<DaymenuEntity, Long> {
    public boolean existsByName(String name);

    public boolean existsByCreation(LocalDate creation);

    @Query("SELECT d.id FROM DaymenuEntity d WHERE d.id IN :ids")
    public Collection<Long> findIdsIn(Collection<Long> ids);

    public Optional<DaymenuEntity> findByCreation(LocalDate creation);

    @Query(
        "SELECT DISTINCT d FROM DaymenuEntity d " +
            "LEFT JOIN FETCH d.categories"
    )
    List<DaymenuEntity> findAllWithCategories();

    @Query(
        "SELECT DISTINCT d FROM DaymenuEntity d " +
            "LEFT JOIN FETCH d.products p " +
            "LEFT JOIN FETCH p.category"
    )
    List<DaymenuEntity> findAllWithSubProducts();

    @Query(
        "SELECT DISTINCT d FROM DaymenuEntity d " +
            "LEFT JOIN FETCH d.products p " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.products"
    )
    List<DaymenuEntity> findAllWithSubProductsAndProducts();
}
