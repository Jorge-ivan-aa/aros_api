package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.TableEntity;

public class TableJpaMapper {
    /**
     * map {@link TableEntity} to {@link Table}
     */
    public static Table toDomain(
        TableEntity source,
        Collection<Order> orders
    ) {
        Table target = new Table();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setOrders(orders);

        return target;
    }

    /**
     * map {@link TableEntity} to {@link Table}
     */
    public static TableEntity toEntity(
        Table source,
        Collection<OrderEntity> orders
    ) {
        TableEntity target = new TableEntity();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setOrders(orders);

        return target;
    }
}
