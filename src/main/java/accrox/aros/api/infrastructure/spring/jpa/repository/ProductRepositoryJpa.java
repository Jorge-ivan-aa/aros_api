package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, Long> {
    public boolean existsByName(String name);

    @Query("SELECT COUNT(c) > 0 FROM ProductEntity c WHERE c.name = :name AND c.id NOT IN :ids")
    public boolean existsByName(String name, Set<Long> ids);

    @Query("SELECT COUNT(c) = :size FROM ProductEntity c WHERE c.id IN :ids")
    public boolean existsAllByIdIn(Set<Long> ids, long size);

    public Optional<ProductEntity> findByName(String name);
    
    @Query(
        "SELECT p FROM ProductEntity p " +
        "LEFT JOIN DaymenuEntity dm " +
        "ON p.id = dm.id " +
        "JOIN p.categories c " +
        "WHERE c IN :categories AND dm.id IS NULL " +
        "GROUP BY p " +
        "HAVING COUNT(c) = :categoryCount"
    )
    public List<ProductEntity> findByCategoriesIn(Set<CategoryEntity> categories, long categoryCount);
    
    @Query(
        "SELECT p FROM ProductEntity p " +
        "LEFT JOIN DaymenuEntity dm " + 
        "ON p.id = dm.id " +
        "WHERE dm.id IS NULL"
    )
    public List<ProductEntity> findNoDayMenuProducts();

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
