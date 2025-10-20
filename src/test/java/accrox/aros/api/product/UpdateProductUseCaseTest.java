package accrox.aros.api.product;

import accrox.aros.api.application.dto.product.UpdateProductInput;
import accrox.aros.api.application.exceptions.product.BadUpdateProductException;
import accrox.aros.api.application.usecases.product.UpdateProductUseCase;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_product_exists_and_new_name_is_valid_then_update_product() throws BadUpdateProductException {
        String currentName = "Pizza";
        String newName = "Deluxe Pizza";

        Product existingProduct = new Product();
        existingProduct.setName(currentName);
        existingProduct.setDescription("Cheese Pizza");
        existingProduct.setPrice(15.0f);
        existingProduct.setPreparationTime(10);

        UpdateProductInput input = new UpdateProductInput(
                currentName, newName, "Extra cheese", 18.0f, 12
        );

        when(productRepository.findByName(currentName)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsByName(newName)).thenReturn(false);

        updateProductUseCase.execute(input);

        verify(productRepository).update(existingProduct);
        assertEquals(newName, existingProduct.getName());
        assertEquals("Extra cheese", existingProduct.getDescription());
        assertEquals(18.0f, existingProduct.getPrice());
        assertEquals(12, existingProduct.getPreparationTime());
    }
}