package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.ProductRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.AreaJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.CategoryJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.ProductJpaMapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        ProductEntity entity = ProductJpaMapper.toEntity(
            product,
            product.getPreparationArea(),
            null
        );
        this.productRepositoryJpa.save(entity);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepositoryJpa
            .findByName(name)
            .map(entity -> ProductJpaMapper.toDomain(entity, null, null));
    }

    @Override
    public Product update(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "Unimplemented method 'update'"
        );
    }

    @Override
    public void UpdateProductCategories(Product product) {
        ProductEntity entity = ProductJpaMapper.toEntity(
            product,
            product.getPreparationArea(),
            product.getCategories()
        );
        this.productRepositoryJpa.save(entity);
    }

    @Override
    public Collection<Product> findAllByIdSimple(Set<Long> ids) {
        Iterable<ProductEntity> entities =
            this.productRepositoryJpa.findAllById(ids);
        Collection<Product> domains = new LinkedList<>();

        for (ProductEntity productEntity : entities) {
            domains.add(ProductJpaMapper.toDomain(productEntity, null, null));
        }

        return domains;
    }

    @Override
    public List<Product> findAll() {
        Iterable<ProductEntity> entities = this.productRepositoryJpa.findAll();
        List<Product> domains = new LinkedList<>();

        for (ProductEntity productEntity : entities) {
            domains.add(ProductJpaMapper.toDomain(productEntity, null, null));
        }

        return domains;
    }

    @Override
    public List<Product> findAllWithRelations() {
        // Load products with areas
        List<ProductEntity> productsWithArea =
            this.productRepositoryJpa.findAllWithArea();

        // Load products with categories
        List<ProductEntity> productsWithCategories =
            this.productRepositoryJpa.findAllWithCategories();

        // Create a map to combine the data
        Map<Long, Product> productMap = new HashMap<>();

        // Process products with areas
        for (ProductEntity productEntity : productsWithArea) {
            Area preparationArea = null;
            if (productEntity.getPreparationArea() != null) {
                preparationArea = AreaJpaMapper.toDomain(
                    productEntity.getPreparationArea(),
                    null
                );
            }

            Product product = ProductJpaMapper.toDomain(
                productEntity,
                preparationArea,
                null
            );
            productMap.put(product.getId(), product);
        }

        // Process products with categories and update the map
        for (ProductEntity productEntity : productsWithCategories) {
            Collection<Category> categories = null;
            if (productEntity.getCategories() != null) {
                categories = productEntity
                    .getCategories()
                    .stream()
                    .map(categoryEntity ->
                        CategoryJpaMapper.toDomain(categoryEntity, null)
                    )
                    .collect(Collectors.toList());
            }

            Product existingProduct = productMap.get(productEntity.getId());
            if (existingProduct != null) {
                existingProduct.setCategories(categories);
            } else {
                // If product wasn't in the area list, create new one
                Product product = ProductJpaMapper.toDomain(
                    productEntity,
                    null,
                    categories
                );
                productMap.put(product.getId(), product);
            }
        }

        // Convert map back to list
        return new LinkedList<>(productMap.values());
    }
}
