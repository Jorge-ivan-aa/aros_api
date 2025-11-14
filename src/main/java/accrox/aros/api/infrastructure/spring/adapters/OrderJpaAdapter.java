package accrox.aros.api.infrastructure.spring.adapters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.ClientOrder;
import accrox.aros.api.domain.model.DayMenuSelection;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderDetailEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.OrderEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.TableEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;
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

    @Override
    @Transactional
    public java.util.Map<Long, Long> findSoldProductQuantitiesFromCompletedOrders() {
        java.util.List<Object[]> rows = this.orderRepositoryJpa.findSoldProductQuantities(OrderStatus.COMPLETED);

        java.util.Map<Long, Long> result = new java.util.LinkedHashMap<>();
        for (Object[] r : rows) {
            Long productId = (Long) r[0];
            Long qty = (r[1] instanceof Long) ? (Long) r[1] : ((Number) r[1]).longValue();
            result.put(productId, qty);
        }
        return result;
    }

    @Override
    public List<Order> findUncompletedOrdersBefore(LocalDateTime today) {
        List<OrderEntity> entities = this.orderRepositoryJpa.findUncompletedEntitiesBefore(today);
        List<Order> domains = new ArrayList<>();

        for (OrderEntity entity : entities) {
            domains.add(OrderJpaMapper.toDomain(entity));
        }

        return domains;
    }

    // TODO: Check GPT code

    @Override
    public void saveAll(List<Order> ordersToSave) {
        if (ordersToSave == null || ordersToSave.isEmpty()) {
            return;
        }

        // 1. Obtenemos los IDs de los objetos de dominio que queremos guardar
        // (Debes tener un getter para el ID en tu objeto de dominio Order)
        List<Long> ids = ordersToSave.stream()
                .map(Order::getId) // O como se llame tu getter de ID
                .collect(Collectors.toList());

        // 2. [FETCH] Buscamos las entidades ORIGINALES y COMPLETAS de la BBDD
        // Estas entidades SÍ tienen el `id_user` y el `id_table` correctos.
        Iterable<OrderEntity> existingEntities = this.orderRepositoryJpa.findAllById(ids);

        // 3. Creamos un mapa para hacer la fusión más eficiente
        Map<Long, Order> domainMap = ordersToSave.stream()
                .collect(Collectors.toMap(Order::getId, order -> order));

        // 4. [MERGE] Actualizamos las entidades existentes con los cambios del dominio
        for (OrderEntity entity : existingEntities) {
            Order domainOrder = domainMap.get(entity.getId());

            if (domainOrder != null) {
                // Actualizamos SOLO los campos que maneja el dominio
                // Asumiendo que tu dominio Order tiene getStatus() y es un Enum
                entity.setStatus(domainOrder.getStatus());
            }
        }

        // 5. [SAVE] Guardamos las entidades originales, completas y ahora modificadas.
        // Hibernate verá que `id_user` y `id_table` no son nulos y la
        // actualización funcionará.
        this.orderRepositoryJpa.saveAll(existingEntities);
    }
}
