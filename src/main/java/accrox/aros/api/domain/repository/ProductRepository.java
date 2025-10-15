package accrox.aros.api.domain.repository;

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

    /**
     * 
     * @param name
     * @return
     */
    public Product findByName(String name);

    /**
     * 
     * @param product
     * @return
     */
    public Product update(Product product);
}