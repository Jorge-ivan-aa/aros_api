package accrox.aros.api.infrastructure.spring.jpa.repository;

import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepositoryJpa
    extends CrudRepository<ProductEntity, Long> {
    public boolean existsByName(String name);

    @Query("SELECT COUNT(c) = :size FROM ProductEntity c WHERE c.id IN :ids")
    public boolean existsAllByIdIn(Set<Long> ids, long size);

    public Optional<ProductEntity> findByName(String name);

    @Query(
        "SELECT DISTINCT p FROM ProductEntity p LEFT JOIN FETCH p.preparationArea"
    )
    List<ProductEntity> findAllWithArea();

    @Query(
        "SELECT DISTINCT p FROM ProductEntity p LEFT JOIN FETCH p.categories"
    )
    List<ProductEntity> findAllWithCategories();

    @Query(
        "SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.preparationArea " +
            "WHERE p.id IN :ids"
    )
    List<ProductEntity> findAllByIdWithArea(Set<Long> ids);

    @Query(
        "SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.categories " +
            "WHERE p.id IN :ids"
    )
    List<ProductEntity> findAllByIdWithCategories(Set<Long> ids);
}
