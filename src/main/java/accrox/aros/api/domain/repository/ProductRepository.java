package accrox.aros.api.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import accrox.aros.api.domain.model.Product;

public interface ProductRepository {
    /**
     * save the information of a product
     * 
     * @param product the product to save
     * 
     * @return the saved info
     */
    public Product create(Product product);

    /**
     * check if exists a product with a given name
     * 
     * @param name name of the product
     * 
     * @return the product exists
     */
    public boolean existsByName(String name);

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

    public boolean existsAllById(Set<Long> ids);

    /**
     * 
     * @param product
     * @return
     */
    public Product update(Product product);

    public void UpdateProductCategories(Product product);

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