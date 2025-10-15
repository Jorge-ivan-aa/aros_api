package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.DayMenuCategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public class DayMenuCategoryJpaMapper {
    public static DayMenuCategory toDomain(
        DayMenuCategoryEntity source,
        Daymenu daymenu,
        Category category,
        Collection<Product> products
    ) {
        if (source == null) return null;

        DayMenuCategory target = new DayMenuCategory();

        target.setId(source.getId());
        target.setDaymenu(daymenu);
        target.setCategory(category);
        target.setProducts(products);
        target.setPosition(source.getPosition());

        return target;
    }

    public static DayMenuCategoryEntity toEntity(
        DayMenuCategory source,
        DaymenuEntity daymenu,
        CategoryEntity category,
        Collection<ProductEntity> products
    ) {
        if (source == null) return null;

        DayMenuCategoryEntity target = new DayMenuCategoryEntity();

        target.setId(source.getId());
        target.setPosition(source.getPosition());
        target.setDaymenu(daymenu);
        target.setCategory(category);
        // target.setProducts(products);
        target.getProducts().addAll(products);

        return target;
    }
}
