package accrox.aros.api.infrastructure.spring.adapters;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.DayMenuSelection;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderDetailEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.OrderRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.ClientOrderDetailJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.ClientOrderJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.OrderJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.ProductJpaMapper;

@Repository
public class OrderJpaAdapter implements OrderRepository {
    @Autowired
    private OrderRepositoryJpa orderRepositoryJpa;

    @Override
    public void create(Order order) {
        OrderEntity entity = OrderJpaMapper.toEntity(order, null, new HashSet<>());

        order.getClientOrders().stream().forEach((co) -> {
            ClientOrderEntity coe = ClientOrderJpaMapper.toEntity(co, new LinkedList<>(), entity);

            Collection<ClientOrderDetailEntity> details = co.getDetails().stream().map((d) -> {
                ProductEntity pe = ProductJpaMapper.toEntity(d, null, null);

                if (d instanceof DayMenuSelection dmSelection) {
                    return ClientOrderDetailJpaMapper.toEntity(dmSelection, coe, pe);
                } else {
                    return ClientOrderDetailJpaMapper.toEntity(d, coe, pe);
                }
            }).toList();

            coe.getDetails().addAll(details);
            entity.getOrders().add(coe);
        });

        this.orderRepositoryJpa.save(entity);
    }
}
