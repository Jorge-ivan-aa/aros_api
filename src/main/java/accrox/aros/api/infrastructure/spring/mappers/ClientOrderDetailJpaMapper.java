package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;
import java.util.LinkedList;

import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.DayMenuSelection;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderDetailEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderEntity;
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
        ClientOrderEntity order,
        ProductEntity product
    ) {
        ClientOrderDetailEntity target = new ClientOrderDetailEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setQuantity(source.getQuantity());
        target.setObservations(source.getObservations());
        target.setProduct(product);
        target.setOrder(order);
        target.setSubProducts(new LinkedList<>());

        return target;
    }

    /**
     * map {@link Daymenu} to {@link ClientOrderDetailJpaMapper}
     */
    public static ClientOrderDetailEntity toEntity(
        Daymenu source,
        ClientOrderEntity order,
        ProductEntity product,
        Collection<OrderDetailSubProductEntity> subproducts
    ) {
        ClientOrderDetailEntity target = ClientOrderDetailJpaMapper.toEntity(source, order, product);

        target.setSubProducts(subproducts);

        return target;
    }

    public static ClientOrderDetailEntity toEntity(
        DayMenuSelection source,
        ClientOrderEntity order,
        ProductEntity referencedProduct
        // Collection<OrderDetailSubProductEntity> subproducts
    ) {
        ClientOrderDetailEntity target = ClientOrderDetailJpaMapper.toEntity((Product) source, order, referencedProduct);

        if (source.getSelection() != null && ! source.getSelection().isEmpty()) {
            source.getSelection().stream().forEach((p) -> {
                OrderDetailSubProductEntity subProduct = new OrderDetailSubProductEntity();
                subProduct.setName(p.getName());
                subProduct.setObservations(p.getObservations());
                subProduct.setDetail(target);

                target.getSubProducts().add(subProduct);
            });
        }
        
        return target;
    }
}
