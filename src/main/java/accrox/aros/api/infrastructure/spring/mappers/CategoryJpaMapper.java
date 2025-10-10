package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public class CategoryJpaMapper {
    /**
     * map {@link CategoryEntity} to {@link Category}
     */
    public static Category toDomain(
        CategoryEntity source,
        Collection<Product> products
    ) {
        Category target = new Category();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setProducts(products);

        return target;
    }

    /**
     * map {@link Category} to {@link CategoryEntity}
     */
    public static CategoryEntity toEntity(
        Category source,
        Collection<ProductEntity> products
    ) {
        CategoryEntity target = new CategoryEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setProducts(products);

        return target;
    }
}
