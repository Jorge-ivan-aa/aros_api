package accrox.aros.api.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.dto.category.CreateCategoryInput;
import accrox.aros.api.application.exceptions.category.CategoryAlreadyExistsException;
import accrox.aros.api.application.usecases.category.CreateCategoryUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {
    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    public void shouldNotCreate() {
        Mockito.when(this.repository.existsByName("tools")).thenReturn(true);

        CreateCategoryInput dto = new CreateCategoryInput("tools");

        Assertions.assertThrows(
            CategoryAlreadyExistsException.class,
            () -> this.createCategoryUseCase.execute(dto)
        );
    }
}
