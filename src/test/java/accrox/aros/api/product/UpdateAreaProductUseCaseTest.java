package accrox.aros.api.product;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.product.UpdateProductAreaInput;
import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateAreaProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private UpdateAreaProductUseCase updateAreaProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_validInput_then_updateProductAreaSuccessfully() {

        Product product = new Product();
        product.setName("Burger");

        Area newArea = new Area();
        newArea.setName("Kitchen");

        GetAreaInput areaInput = new GetAreaInput("Kitchen");
        UpdateProductAreaInput input = new UpdateProductAreaInput("Burger", areaInput);

        when(productRepository.findByName("Burger")).thenReturn(Optional.of(product));
        when(areaRepository.getAreaByName("Kitchen")).thenReturn(Optional.of(newArea));

        updateAreaProductUseCase.execute(input);

        assertEquals(newArea, product.getPreparationArea());
        verify(productRepository, times(1)).updateProductArea(product);
    }

    @Test
    void when_productNotFound_then_throwValidationException() {
        GetAreaInput areaInput = new GetAreaInput("Kitchen");
        UpdateProductAreaInput input = new UpdateProductAreaInput("NonExistent", areaInput);

        when(productRepository.findByName("NonExistent")).thenReturn(Optional.empty());

        ValidationException ex = assertThrows(ValidationException.class, () ->
                updateAreaProductUseCase.execute(input));

        assertEquals("No user found with the provided name", ex.getMessage());
        verify(productRepository, never()).updateProductArea(any());
    }

    @Test
    void when_areaNotFound_then_throwValidationException() {

        Product product = new Product();
        product.setName("Pizza");

        GetAreaInput areaInput = new GetAreaInput("NonExistentArea");
        UpdateProductAreaInput input = new UpdateProductAreaInput("Pizza", areaInput);

        when(productRepository.findByName("Pizza")).thenReturn(Optional.of(product));
        when(areaRepository.getAreaByName("NonExistentArea")).thenReturn(Optional.empty());

        ValidationException ex = assertThrows(ValidationException.class, () ->
                updateAreaProductUseCase.execute(input));

        assertTrue(ex.getMessage().contains("Area not found"));
        verify(productRepository, never()).updateProductArea(any());
    }


}
