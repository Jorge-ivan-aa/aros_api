package accrox.aros.api.domain.repository;

import java.util.Optional;
import java.util.Set;

import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;

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

    /**
     *
     * @param product
     * @param area
     */
    void updateUserArea(Product product, AreaEntity area);

    /**
     *
     * @param name
     * @return Product
     */
    public Optional<Product> findByName(String name);

    public boolean existsAllById(Set<Long> ids);

    /**
     * 
     * @param product
     * @return
     */
    public Product update(Product product);
}