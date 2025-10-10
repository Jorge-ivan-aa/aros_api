package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public class ProductJpaMapper {
    /**
     * map {@link ProductEntity} to {@link Product}
     */
    public static Product toDomain(
        ProductEntity source,
        Area preparationArea,
        Collection<Category> categories
    ) {
        Product target = new Product();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setActive(source.getActive());
        target.setPreparationTime(source.getPreparationTime());
        target.setPreparationArea(preparationArea);
        target.setCategories(categories);

        return target;
    }

    /**
     * map {@link Product} to {@link ProductEntity}
     */
    public static ProductEntity toEntity(
        Product source,
        AreaEntity preparationArea,
        Collection<CategoryEntity> categories
    ) {
        ProductEntity target = new ProductEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setActive(source.getActive());
        target.setPreparationTime(source.getPreparationTime());
        target.setPreparationArea(preparationArea);
        target.setCategories(categories);

        return target;
    }
}
