package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Order;

import java.util.List;

public interface OrderRepository {

    public void MarkOrderAsCompleted(Long orderId);

    public void create(Order order);

    public List<Order> findAll();
    
    /**
     * find all the orders taken by a determinate user
     * 
     * @param responsibleId user's id
     *
     * @return orders taken by the user
     */
    public List<Order> findAllByResponsible(Long responsibleId);
}