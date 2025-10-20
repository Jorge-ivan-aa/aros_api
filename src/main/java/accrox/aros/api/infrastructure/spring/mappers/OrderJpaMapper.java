package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;
import java.util.Set;

import accrox.aros.api.domain.model.ClientOrder;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.TableEntity;

public class OrderJpaMapper {
    /**
     * map {@link OrderEntity} to {@link Order}
     */
    public static Order toDomain(
        OrderEntity source,
        Table table,
        Collection<ClientOrder> clientOrders
    ) {
        Order target = new Order();

        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setTakedAt(source.getTakedAt());
        target.setTable(table);
        target.setClientOrders(clientOrders);
        target.setTotal(source.getTotal());

        return target;
    }

    /**
     * map {@link Order} to {@link OrderEntity}
     */
    public static OrderEntity toEntity(
        Order source,
        TableEntity table,
        Set<ClientOrderEntity> clientOrders
    ) {
        OrderEntity target = new OrderEntity();

        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setTakedAt(source.getTakedAt());
        target.setTable(table);
        target.setOrders(clientOrders);
        target.setTotal(source.getTotal());

        return target;
    }
}
