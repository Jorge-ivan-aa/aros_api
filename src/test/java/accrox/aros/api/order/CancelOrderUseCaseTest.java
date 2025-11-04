package accrox.aros.api.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.usecases.order.CancelOrderUseCase;
import accrox.aros.api.domain.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class CancelOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CancelOrderUseCase useCase;

    @Test
    public void shouldCancelOrderSuccessfully() {
        Mockito.doNothing().when(orderRepository).cancelOrder(Mockito.anyLong());

        Assertions.assertDoesNotThrow(() -> useCase.execute(1L));
        Mockito.verify(orderRepository, Mockito.times(1)).cancelOrder(1L);
    }

    @Test
    public void shouldThrowExceptionWhenOrderNotFound() {
        Mockito.doThrow(new IllegalArgumentException("Order not found"))
                .when(orderRepository).cancelOrder(Mockito.anyLong());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(1L);
        });
    }
}
