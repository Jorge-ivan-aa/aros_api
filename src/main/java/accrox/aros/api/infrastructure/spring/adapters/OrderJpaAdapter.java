package accrox.aros.api.infrastructure.spring.adapters;

import java.util.*;

import accrox.aros.api.domain.model.Table;
import accrox.aros.api.infrastructure.spring.jpa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.DayMenuSelection;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.OrderRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.ClientOrderDetailJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.ClientOrderJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.OrderJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.ProductJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.TableJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.UserJpaMapper;
import jakarta.transaction.Transactional;

@Repository
public class OrderJpaAdapter implements OrderRepository {
    @Autowired
    private OrderRepositoryJpa orderRepositoryJpa;

    @Override
    public void MarkOrderAsCompleted(Long orderId) {
        OrderEntity orderEntity = this.orderRepositoryJpa.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderEntity.setStatus(OrderStatus.COMPLETED);
        this.orderRepositoryJpa.save(orderEntity);
    }

    @Transactional
    public void create(Order order) {
        OrderEntity entity = OrderJpaMapper.toEntity(order, null, new HashSet<>(), null);
        entity.setTable(TableJpaMapper.toEntity(order.getTable(), null));
        entity.setResponsible(UserJpaMapper.toEntity(order.getResponsible(), null, null, null));

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

    @Override
    @Transactional
    public List<Order> findAll() {
        Iterable<OrderEntity> entities = orderRepositoryJpa.findAll();

        if (entities == null) {
            return Collections.emptyList();
        }

        List<Order> orders = new ArrayList<>();

        for (OrderEntity entity : entities) {
            Table table = TableJpaMapper.toDomain(entity.getTable(),null);
            orders.add(OrderJpaMapper.toDomain(entity, table, null, null));
        }

        return orders;
    }
    
    @Override
    @Transactional
    public List<Order> findAllByResponsible(Long responsibleId) {
        return this.orderRepositoryJpa.findAllByResponsibleId(responsibleId)
            .stream()
            .map((o) -> {
                Table t = TableJpaMapper.toDomain(o.getTable(), null);

                return OrderJpaMapper.toDomain(o, t, null, null);
            })
            .toList();
    }
}
