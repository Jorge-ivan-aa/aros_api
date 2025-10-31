package accrox.aros.api.order;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.order.CreateClientOrderInput;
import accrox.aros.api.application.dto.order.CreateOrderDetailInput;
import accrox.aros.api.application.dto.order.CreateOrderInput;
import accrox.aros.api.application.exceptions.order.EmptyDayMenuSelectionException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.application.usecases.order.CreateOrderUseCase;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.domain.repository.TableRepository;
import io.jsonwebtoken.lang.Collections;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private DaymenuRepository daymenuRepository;

    @Mock
    private TableRepository tableRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Test
    public void shouldFailFindingNonExistentProducts() {
        Mockito.when(productRepository.findAllByIdSimple(Mockito.anySet())).thenReturn(Collections.emptySet());
        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(true);

        CreateOrderInput input = this.makeOrderInput();

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            useCase.execute(input, null);
        });
    }

    @Test
    public void shouldFailIfExistsDaymenuWithoutSelection() {
        Mockito.when(productRepository.findAllByIdSimple(Mockito.anySet())).thenReturn(
            List.of(new Product(1L, null, null, null, null))
        );

        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(true);
        /* 
         * this only work if the order input has un detail for product with id 1L
         * and a {@code null} collection of subproducts */
        Mockito.when(daymenuRepository.findIdsIn(Mockito.anyCollection())).thenReturn(List.of(1L));

        CreateOrderInput input = this.makeOrderInput();

        Assertions.assertThrows(EmptyDayMenuSelectionException.class, () -> {
            useCase.execute(input, null);
        });
    }

    @Test
    public void shouldFailFindingNonExistentTable() {
        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(false);

        CreateOrderInput input = this.makeOrderInput();

        Assertions.assertThrows(TableNotFoundException.class, () -> {
            useCase.execute(input, null);
        });
    }

    @Test
    public void shouldWorkCorrectly() {
        Mockito.when(productRepository.findAllByIdSimple(Mockito.anySet())).thenReturn(
            List.of(new Product(1L, null, null, 1200.0F, 2))
        );

        Mockito.when(tableRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(daymenuRepository.findIdsIn(Mockito.anyCollection())).thenReturn(List.of());
        // Mockito.doNothing(orderRepository).create(Mockito.any(Order.class));

        Mockito.doNothing().when(orderRepository).create(Mockito.any());

        CreateOrderInput input = this.makeOrderInput();
        User responsible = new User();
        responsible.setId(1L);

        Assertions.assertDoesNotThrow(() -> useCase.execute(input, responsible));
    }

    private CreateOrderInput makeOrderInput() {
        CreateClientOrderInput oi1 = new CreateClientOrderInput(
            List.of(
                new CreateOrderDetailInput(1L, 2, null, null)
            )
        );

        CreateOrderInput input = new CreateOrderInput(
            1L,
            List.of(oi1)
        );

        return input;
    }
}
