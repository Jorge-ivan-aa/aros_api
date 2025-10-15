package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderDetailEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderDetailSubProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public class ClientOrderDetailJpaMapper {
    /**
     * map {@link ClientOrderDetailEntity} to {@link Product}
     */
    public static Product toDomain(
        ClientOrderDetailEntity source
    ) {
        Product target = new Product();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setQuantity(source.getQuantity());
        target.setObservations(source.getObservations());

        return target;
    }

    /**
     * map {@link ClientOrderDetailEntity} to {@link Daymenu}
     */
    public static Daymenu toDomain(
        ClientOrderDetailEntity source,
        Collection<DayMenuCategory> subproducts
    ) {
        Daymenu target = new Daymenu();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setQuantity(source.getQuantity());
        target.setObservations(source.getObservations());
        target.setSubProducts(subproducts);

        return target;
    }

    /**
     * map {@link Product} to {@link ClientOrderDetailEntity}
     */
    public static ClientOrderDetailEntity toEntity(
        Product source,
        ProductEntity product
    ) {
        ClientOrderDetailEntity target = new ClientOrderDetailEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setQuantity(source.getQuantity());
        target.setObservations(source.getObservations());
        target.setProduct(product);
        target.setSubProducts(null);

        return target;
    }

    /**
     * map {@link Daymenu} to {@link ClientOrderDetailJpaMapper}
     */
    public static ClientOrderDetailEntity toEntity(
        Daymenu source,
        DaymenuEntity product,
        Collection<OrderDetailSubProductEntity> subproducts
    ) {
        ClientOrderDetailEntity target = ClientOrderDetailJpaMapper.toEntity(source, product);

        target.setSubProducts(subproducts);

        return target;
    }
}
