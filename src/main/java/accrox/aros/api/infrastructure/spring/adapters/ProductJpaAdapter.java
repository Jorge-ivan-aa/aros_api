package accrox.aros.api.infrastructure.spring.adapters;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.ProductRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.ProductJpaMapper;

@Repository
public class ProductJpaAdapter implements ProductRepository {
    @Autowired
    private ProductRepositoryJpa productRepositoryJpa;

    @Override
    public Product create(Product product) {
        ProductEntity entity = ProductJpaMapper.toEntity(product, null, null);
        ProductEntity saved = this.productRepositoryJpa.save(entity);

        return ProductJpaMapper.toDomain(saved, null, null);
    }

    @Override
    public boolean existsByName(String name) {
        return this.productRepositoryJpa.existsByName(name);
    }

    @Override
    public boolean existsAllById(Set<Long> ids) {
        return this.productRepositoryJpa.existsAllByIdIn(ids, ids.size());
    }

    @Override
    public void updateProductArea(Product product) {
        ProductEntity entity = ProductJpaMapper.toEntity(product, product.getPreparationArea(), null);
        this.productRepositoryJpa.save(entity);

    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepositoryJpa.findByName(name)
                .map(entity -> ProductJpaMapper.toDomain(entity, null, null));
    }

    @Override
    public Product update(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void UpdateProductCategories(Product product) {
        ProductEntity entity=ProductJpaMapper.toEntity(product, product.getPreparationArea(), product.getCategories());
        this.productRepositoryJpa.save(entity);
    }
    
    public Collection<Product> findAllByIdSimple(Set<Long> ids) {
        Iterable<ProductEntity> entities = this.productRepositoryJpa.findAllById(ids);
        Collection<Product> domains = new LinkedList<>();

        for (ProductEntity productEntity : entities) {
            domains.add(ProductJpaMapper.toDomain(productEntity, null, null));
        }

        return domains;
    }
}
