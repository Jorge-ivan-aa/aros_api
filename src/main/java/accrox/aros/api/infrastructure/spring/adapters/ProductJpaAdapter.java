package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import accrox.aros.api.infrastructure.spring.mappers.UserJpaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.ProductRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.ProductJpaMapper;

import java.util.Optional;

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
    public void updateUserArea(Product product, AreaEntity area) {
        ProductEntity entity = ProductJpaMapper.toEntity(product, area, null);
        this.productRepositoryJpa.save(entity);

    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepositoryJpa.findByName(name)
                .map(entity -> ProductJpaMapper.toDomain(entity, null, null));
    }
}
