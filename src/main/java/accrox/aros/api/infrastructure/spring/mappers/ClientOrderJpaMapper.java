package accrox.aros.api.infrastructure.spring.mappers;

import java.util.Collection;

import accrox.aros.api.domain.model.ClientOrder;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderDetailEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;

public class ClientOrderJpaMapper {
    /**
     * map {@link ClientOrderEntity} to {@link ClientOrder}
     */
    public static ClientOrder toDomain(
        ClientOrderEntity source,
        Collection<Product> details,
        Order order
    ) {
        ClientOrder target = new ClientOrder();

        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setDetails(details);
        target.setOrder(order);

        return target;
    }

    /**
     * map {@link ClientOrder} to {@link ClientOrderEntity}
     */
    public static ClientOrderEntity toEntity(
        ClientOrder source,
        Collection<ClientOrderDetailEntity> details,
        OrderEntity order
    ) {
        ClientOrderEntity target = new ClientOrderEntity();

        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setDetails(details);
        target.setOrder(order);

        return target;
    }
}
