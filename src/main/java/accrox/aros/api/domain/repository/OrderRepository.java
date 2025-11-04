package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    public void MarkOrderAsCompleted(Long orderId);

    public void create(Order order);

    public List<Order> findAll();

    /**
     * Find all orders with their complete details (client orders, products, table,
     * responsible)
     * 
     * @return list of orders with all details loaded
     */
    public List<Order> findDetailAll();

    /**
     * find all the orders taken by a determinate user
     * 
     * @param responsibleId user's id
     *
     * @return orders taken by the user
     */
    public List<Order> findAllByResponsible(Long responsibleId);

    /**
     * Find an order by its ID
     * 
     * @param id order's id
     * @return Optional containing the order if found
     */
    public Optional<Order> findById(Long id);

    /**
     * Update an existing order
     * 
     * @param order the order to update
     */
    public void update(Order order);

    /**
     * Cancel an order by setting its status to CANCELLED
     * 
     * @param orderId the order's id
     * @throws IllegalArgumentException if order not found
     */
    public void cancelOrder(Long orderId);
}