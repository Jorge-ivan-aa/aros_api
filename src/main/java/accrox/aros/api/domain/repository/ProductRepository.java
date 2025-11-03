package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Product;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {
    Product create(Product product);

    boolean existsByName(String name);

    boolean existsAllById(Set<Long> ids);

    void updateProductArea(Product product);

    Product update(Product product);

    void UpdateProductCategories(Product product);

    List<Product> findAll();

    /**
     * Find all products with their relations (areas and categories) loaded
     */
    List<Product> findAllWithRelations();

    Optional<Product> findByName(String name);

    /**
     * Find all products by their IDs without loading relations
     */
    Collection<Product> findAllByIdSimple(Set<Long> ids);
}
