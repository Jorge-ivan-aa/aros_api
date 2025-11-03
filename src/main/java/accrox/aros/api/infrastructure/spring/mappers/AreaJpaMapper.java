package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public class AreaJpaMapper {
    /**
     * map {@link AreaEntity} to {@link Area}
     */
    public static Area toDomain(
        AreaEntity source,
        Collection<Product> products
    ) {
        if (source == null) return null;

        Area target = new Area();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setProducts(products);

        return target;
    }

    /**
     * map {@link Area} to {@link AreaEntity}
     */
    public static AreaEntity toEntity(
        Area source,
        Collection<ProductEntity> products
    ) {
        AreaEntity target = new AreaEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setProducts(products);

        return target;
    }
}
