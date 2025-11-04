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

    public boolean existsByNameIgnoring(String name, Set<Long> ids);

    /**
     *
     * @param product
     */
    void updateProductArea(Product product);

    /**
     *
     * @param name
     * @return Product
     */
    public Optional<Product> findByName(String name);
    
    public Optional<Product> findById(Long id);

    Product update(Product product);

    void UpdateProductCategories(Product product);

    /**
     * Find all products with their relations (areas and categories) loaded
     */
    List<Product> findAllWithRelations();

    public Collection<Product> findAllByIdSimple(Set<Long> ids);
    
    public List<Product> findAll();
    
    public List<Product> findByCategories(Set<Long> categories);
    
    /**
     * obtains register that are only products (no herence)
     *
     * @return
     */
    public List<Product> getNoDayMenuProducts();
}
