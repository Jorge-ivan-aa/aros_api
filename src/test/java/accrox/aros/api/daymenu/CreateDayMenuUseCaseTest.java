package accrox.aros.api.daymenu;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.daymenu.CreateDayMenuInput;
import accrox.aros.api.application.dto.daymenu.DayMenuCategorySimple;
import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.usecases.product.CreateDayMenuUseCase;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CreateDayMenuUseCaseTest {
    @Mock
    private DaymenuRepository daymenuRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateDayMenuUseCase useCase;

    @Test
    public void ShouldFailFindingNonExistentProducts() {
        Mockito.when(categoryRepository.existsAllById(Mockito.anyCollection())).thenReturn(true);
        Mockito.when(productRepository.existsAllById(Mockito.anySet())).thenReturn(false);

        CreateDayMenuInput input = new CreateDayMenuInput(
            "Daymenu #1", 
            18000.0F,
            List.of(
                new DayMenuCategorySimple(1L, (short) 1, List.of(1L, 2L)),
                new DayMenuCategorySimple(2L, (short) 2, List.of(3L, 4L))
            )
        );

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    public void ShouldFailFindingNonExistentCategory() {
        Mockito.when(categoryRepository.existsAllById(Mockito.anyCollection())).thenReturn(false);

        CreateDayMenuInput input = new CreateDayMenuInput(
            "Daymenu #1", 
            18000.0F,
            List.of(
                new DayMenuCategorySimple(1L, (short) 1, List.of(1L, 2L)),
                new DayMenuCategorySimple(2L, (short) 2, List.of(3L, 4L))
            )
        );

        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    public void shouldFailUsingExistentName() {
        Mockito.when(daymenuRepository.existsByName("Daymenu #1")).thenReturn(true);

        CreateDayMenuInput input = new CreateDayMenuInput(
            "Daymenu #1", 
            18000.0F,
            List.of(
                new DayMenuCategorySimple(1L, (short) 1, List.of(1L, 2L)),
                new DayMenuCategorySimple(2L, (short) 2, List.of(3L, 4L))
            )
        );

        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    public void shouldNotFail() {
        Mockito.when(categoryRepository.existsAllById(Mockito.anyCollection())).thenReturn(true);
        Mockito.when(productRepository.existsAllById(Mockito.anySet())).thenReturn(true);
        Mockito.doNothing().when(daymenuRepository).create(Mockito.any(Daymenu.class));

        CreateDayMenuInput input = new CreateDayMenuInput(
            "Daymenu #1", 
            18000.0F,
            List.of(
                new DayMenuCategorySimple(1L, (short) 1, List.of(1L, 2L)),
                new DayMenuCategorySimple(2L, (short) 2, List.of(3L, 4L))
            )
        );

        Assertions.assertDoesNotThrow(() -> {
            useCase.execute(input);
        });
    }
}
