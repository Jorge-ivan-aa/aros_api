package accrox.aros.api.order;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.order.OrdersOutput;
import accrox.aros.api.application.usecases.order.GetOrdersByResponsibleUseCase;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class GetOrdersByResponsibleUseCaseTest {
    @Mock
    private OrderRepository repository;
    
    @InjectMocks
    private GetOrdersByResponsibleUseCase useCase;
    
    @Test
    public void shouldRetrieveAllOrdersOrders() {
        Table table1 = new Table();
        table1.setName("Mesa 1");
        
        User responsible1 = new User();
        responsible1.setId(1L);
        responsible1.setName("Responsible 1");

        User responsible2 = new User();
        responsible1.setId(1L);
        responsible1.setName("Responsible 2");

        Order order1 = new Order();
        order1.setId(1L);
        order1.setTotal(37000f);
        order1.setStatus(OrderStatus.PENDING);
        order1.setTakedAt(LocalDateTime.now());
        order1.setTable(table1);
        order1.setResponsible(responsible1);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setStatus(OrderStatus.COMPLETED); // ✅ corregido
        order2.setTakedAt(LocalDateTime.now());
        order2.setTotal(33000f);
        order1.setTable(table1);
        order2.setResponsible(responsible2);
        
        Mockito.when(repository.findAllByResponsible(1L)).thenReturn(List.of(order1));
        
        List<OrdersOutput> ouput = Assertions.assertDoesNotThrow(() -> useCase.execute(1L));
        
        Assertions.assertEquals(ouput.get(0).totalPrice(), order1.getTotal());
    }
}
