package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public class DaymenuJpaMapper {
    /**
     * map {@link DaymenuEntity} to {@link Daymenu}
     */
    public static Daymenu toDomain(
        DaymenuEntity source,
        Collection<Category> categories,
        Collection<Product> subProducts
    ) {
        Daymenu target = new Daymenu();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setActive(source.getActive());
        target.setPreparationTime(source.getPreparationTime());
        target.setPreparationArea(null);
        target.setCreation(source.getCreation());
        target.setCategories(categories);
        target.setSubProducts(subProducts);

        return target;
    }

    /**
     * map {@link Daymenu} to {@link DaymenuEntity}
     */
    public static DaymenuEntity toEntity(
        Daymenu source,
        Collection<CategoryEntity> categories,
        Collection<ProductEntity> products
    ) {
        DaymenuEntity target = new DaymenuEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setActive(source.getActive());
        target.setPreparationTime(source.getPreparationTime());
        target.setPreparationArea(null);
        target.setCreation(source.getCreation());
        target.setCategories(categories);
        target.setProducts(products);

        return target;
    }
}
