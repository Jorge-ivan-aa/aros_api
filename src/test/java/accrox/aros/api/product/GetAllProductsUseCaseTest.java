package accrox.aros.api.product;

import accrox.aros.api.application.usecases.product.GetAllProductsUseCase;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllProductsUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private GetAllProductsUseCase useCase;

    @Test
    void whenProductsExist_thenReturnAll() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Coffee");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Tea");

        List<Product> list = new java.util.ArrayList<>();
        list.add(p1);
        list.add(p2);

        when(repository.findAll()).thenReturn(list);

        List<Product> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals("Coffee", result.get(0).getName());
        assertEquals("Tea", result.get(1).getName());
    }

}
