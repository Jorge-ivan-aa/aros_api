package accrox.aros.api.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.product.CreateProductInput;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CreateProductUseCaseTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private CreateProductUseCase useCase;

    @Test
    public void shouldNotCreateIfExists() {
        Mockito.when(repository.existsByName("Spoon")).thenReturn(true);

        CreateProductInput input = new CreateProductInput(
            "Spoon",
            "The greatest tool in the world",
            1200.0F,
            3,
            1L
        );

        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> {
            this.useCase.execute(input);
        });
    }

    @Test
    public void shouldCreate() {
        // setup mocks for test
        Mockito.when(repository.existsByName("Spoon")).thenReturn(false);
        Mockito.when(repository.create(Mockito.any(Product.class))).thenReturn(null);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        // example input for use case
        CreateProductInput input = new CreateProductInput(
            "Spoon",
            "The greatest tool in the world",
            1200.0F,
            3,
            1L
        );

        // check the call don't fail
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(input));

        // capture the argument passed to the method
        Mockito.verify(repository, Mockito.times(1)).create(productCaptor.capture());

        Product saved = productCaptor.getValue();

        // check the input and the argument passed to repository matches
        Assertions.assertEquals(input.name(), saved.getName());
        Assertions.assertEquals(input.description(), saved.getDescription());
        Assertions.assertEquals(input.price(), saved.getPrice());
        Assertions.assertEquals(input.preparationTime(), saved.getPreparationTime());
    }
}
