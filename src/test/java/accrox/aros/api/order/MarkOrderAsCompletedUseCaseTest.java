package accrox.aros.api.order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;
import accrox.aros.api.domain.repository.OrderRepository;


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