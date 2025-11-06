package accrox.aros.api.infrastructure.spring.adapters;

import java.util.*;

import accrox.aros.api.domain.model.*;
import accrox.aros.api.infrastructure.spring.jpa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.OrderRepositoryJpa;
import accrox.aros.api.infrastructure.spring.jpa.repository.TableRepositoryJpa;
import accrox.aros.api.infrastructure.spring.jpa.repository.UserRepositoryJpa;
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

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private TableRepositoryJpa tableRepositoryJpa;

    @Override
    public void MarkOrderAsCompleted(Long orderId) {
        OrderEntity orderEntity = this.orderRepositoryJpa.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderEntity.setStatus(OrderStatus.COMPLETED);
        this.orderRepositoryJpa.save(orderEntity);
    }

    @Transactional
    public void create(Order order) {
        OrderEntity entity = OrderJpaMapper.toEntity(order, null, new HashSet<>(), null);
        // Use managed references to avoid unintended updates on related entities
        if (order.getTable() != null && order.getTable().getId() != null) {
            TableEntity tableRef = this.tableRepositoryJpa.findById(order.getTable().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Table not found"));
            entity.setTable(tableRef);
        }

        // Resolve responsible user either by ID (preferred) or by document
        if (order.getResponsible() != null) {
            if (order.getResponsible().getId() != null) {
                UserEntity userRef = this.userRepositoryJpa
                        .findById(order.getResponsible().getId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                entity.setResponsible(userRef);
            } else if (order.getResponsible().getDocument() != null
                    && !order.getResponsible().getDocument().isBlank()) {
                UserEntity userRef = this.userRepositoryJpa
                        .findByDocument(order.getResponsible().getDocument())
                        .orElseThrow(() -> new IllegalArgumentException("User not found by document"));
                entity.setResponsible(userRef);
            } else {
                throw new IllegalArgumentException("Responsible user is required");
            }
        }

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
        List<Order> orders = new ArrayList<>();

        for (OrderEntity entity : entities) {
            Table table = TableJpaMapper.toDomain(entity.getTable(), null);
            orders.add(OrderJpaMapper.toDomain(entity, table, null, null));
        }

        return orders;
    }

    @Transactional
    @Override
    public List<Order> findDetailAll() {
        List<OrderEntity> entities = orderRepositoryJpa.findAllWithDetails();
        List<Order> orders = new ArrayList<>();

        for (OrderEntity entity : entities) {
            // Mapeamos la mesa
            Table table = entity.getTable() != null
                    ? TableJpaMapper.toDomain(entity.getTable(), null)
                    : null;

            // Mapeamos las órdenes de cliente (ClientOrder)
            List<ClientOrder> clientOrders = entity.getOrders() == null
                    ? Collections.emptyList()
                    : entity.getOrders().stream()
                            .map(coEntity -> {
                                // Mapeamos los detalles (productos) de cada ClientOrder
                                List<Product> details = coEntity.getDetails() == null
                                        ? Collections.emptyList()
                                        : coEntity.getDetails().stream()
                                                .map(ClientOrderDetailJpaMapper::toDomain)
                                                .toList();

                                return ClientOrderJpaMapper.toDomain(coEntity, details, null);
                            })
                            .toList();

            // Mapeamos el responsable usando UserJpaMapper
            User responsible = UserJpaMapper.toDomain(
                    entity.getResponsible(),
                    null, // areas, si quieres podrías traerlas con un join fetch
                    null, // tokens
                    null // orders, no los necesitamos aquí
            );

            // Mapeamos la orden completa con sus relaciones
            Order order = OrderJpaMapper.toDomain(
                    entity,
                    table,
                    clientOrders,
                    responsible);

            // Vinculamos las ClientOrders con su Order
            clientOrders.forEach(co -> co.setOrder(order));

            orders.add(order);
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

    @Override
    @Transactional
    public Optional<Order> findById(Long id) {
        return this.orderRepositoryJpa.findById(id)
                .map(entity -> {
                    Table table = TableJpaMapper.toDomain(entity.getTable(), null);
                    return OrderJpaMapper.toDomain(entity, table, null, null);
                });
    }

    @Override
    @Transactional
    public void update(Order order) {
        OrderEntity entity = this.orderRepositoryJpa.findById(order.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() != null) {
            entity.setStatus(order.getStatus());
        }

        if (order.getTable() != null && order.getTable().getId() != null) {
            TableEntity tableRef = this.tableRepositoryJpa.findById(order.getTable().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Table not found"));
            entity.setTable(tableRef);
        }

        if (order.getResponsible() != null && order.getResponsible().getId() != null) {
            UserEntity userRef = this.userRepositoryJpa.findById(order.getResponsible().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            entity.setResponsible(userRef);
        }

        this.orderRepositoryJpa.save(entity);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        OrderEntity orderEntity = this.orderRepositoryJpa.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderEntity.setStatus(OrderStatus.CANCELLED);
        this.orderRepositoryJpa.save(orderEntity);
    }
}
