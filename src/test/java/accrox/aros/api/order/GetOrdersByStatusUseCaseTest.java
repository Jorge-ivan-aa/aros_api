package accrox.aros.api.order;


import accrox.aros.api.application.dto.order.OrdersOutput;
import accrox.aros.api.application.usecases.order.GetOrdersByStatusUseCase;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetOrdersByStatusUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private GetOrdersByStatusUseCase getOrdersByStatusUseCase;

    @Test
    void whenOrdersExistWithGivenStatus_thenReturnFilteredOrders() {

        Table table1 = new Table();
        table1.setName("Mesa 1");

        Order order1 = new Order();
        order1.setId(1L);
        order1.setStatus(OrderStatus.PENDING);
        order1.setTakedAt(LocalDateTime.now());
        order1.setTable(table1);
        order1.setTotal(37000f);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setStatus(OrderStatus.COMPLETED); // ✅ corregido
        order2.setTakedAt(LocalDateTime.now());
        order2.setTable(table1);
        order2.setTotal(33000f);

        List<Order> mockOrders = List.of(order1, order2);

        when(orderRepository.findAll()).thenReturn(mockOrders);

        List<OrdersOutput> result = getOrdersByStatusUseCase.execute("PENDING");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).status());
        assertEquals("Mesa 1", result.get(0).table());
        assertEquals(37000f, result.get(0).totalPrice());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void whenInvalidStatus_thenReturnAllOrders() {

        Order order1 = new Order();
        order1.setId(1L);
        order1.setStatus(OrderStatus.PENDING);
        order1.setTotal(10000f);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setStatus(OrderStatus.CANCELLED);
        order2.setTotal(20000f);

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<OrdersOutput> result = getOrdersByStatusUseCase.execute("INVALID_STATUS");

        assertNotNull(result);
        assertEquals(2, result.size()); // devuelve todas
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void whenNoOrdersExist_thenReturnEmptyList() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<OrdersOutput> result = getOrdersByStatusUseCase.execute("PENDING");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }
}