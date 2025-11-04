package accrox.aros.api.order;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.order.UpdateOrderInput;
import accrox.aros.api.application.exceptions.order.OrderNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.application.usecases.order.UpdateOrderUseCase;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.TableRepository;
import accrox.aros.api.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TableRepository tableRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateOrderUseCase useCase;

    @Test
    public void shouldFailWhenOrderNotFound() {
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        UpdateOrderInput input = new UpdateOrderInput(1L, OrderStatus.COMPLETED, null, null);

        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    public void shouldFailWhenTableNotFound() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setStatus(OrderStatus.PENDING);

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingOrder));
        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(false);

        UpdateOrderInput input = new UpdateOrderInput(1L, null, 2L, null);

        Assertions.assertThrows(TableNotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    public void shouldFailWhenUserNotFound() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setStatus(OrderStatus.PENDING);

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingOrder));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        UpdateOrderInput input = new UpdateOrderInput(1L, null, null, 2L);

        Assertions.assertThrows(RuntimeException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    public void shouldUpdateOrderStatusSuccessfully() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setStatus(OrderStatus.PENDING);

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingOrder));
        Mockito.doNothing().when(orderRepository).update(Mockito.any(Order.class));

        UpdateOrderInput input = new UpdateOrderInput(1L, OrderStatus.COMPLETED, null, null);

        Assertions.assertDoesNotThrow(() -> useCase.execute(input));
        Mockito.verify(orderRepository, Mockito.times(1)).update(Mockito.any(Order.class));
    }

    @Test
    public void shouldUpdateOrderTableSuccessfully() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setTable(new Table(1L, null, null));

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingOrder));
        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(orderRepository).update(Mockito.any(Order.class));

        UpdateOrderInput input = new UpdateOrderInput(1L, null, 2L, null);

        Assertions.assertDoesNotThrow(() -> useCase.execute(input));
        Mockito.verify(orderRepository, Mockito.times(1)).update(Mockito.any(Order.class));
    }

    @Test
    public void shouldUpdateOrderResponsibleSuccessfully() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        User existingUser = new User();
        existingUser.setId(1L);
        existingOrder.setResponsible(existingUser);

        User newUser = new User();
        newUser.setId(2L);

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingOrder));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newUser));
        Mockito.doNothing().when(orderRepository).update(Mockito.any(Order.class));

        UpdateOrderInput input = new UpdateOrderInput(1L, null, null, 2L);

        Assertions.assertDoesNotThrow(() -> useCase.execute(input));
        Mockito.verify(orderRepository, Mockito.times(1)).update(Mockito.any(Order.class));
    }

    @Test
    public void shouldUpdateMultipleFieldsSuccessfully() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setStatus(OrderStatus.PENDING);
        existingOrder.setTable(new Table(1L, null, null));
        User existingUser = new User();
        existingUser.setId(1L);
        existingOrder.setResponsible(existingUser);

        User newUser = new User();
        newUser.setId(2L);

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingOrder));
        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newUser));
        Mockito.doNothing().when(orderRepository).update(Mockito.any(Order.class));

        UpdateOrderInput input = new UpdateOrderInput(1L, OrderStatus.COMPLETED, 2L, 2L);

        Assertions.assertDoesNotThrow(() -> useCase.execute(input));
        Mockito.verify(orderRepository, Mockito.times(1)).update(Mockito.any(Order.class));
    }
}
