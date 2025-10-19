package accrox.aros.api.order;

import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;
import accrox.aros.api.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


class MarkOrderAsCompletedUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_executeWithValidOrderId_then_markOrderAsCompletedCalled() {
        Long orderId = 1L;

        markOrderAsCompletedUseCase.execute(orderId);

        verify(orderRepository, times(1)).MarkOrderAsCompleted(orderId);
    }

}