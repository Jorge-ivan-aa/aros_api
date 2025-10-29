package accrox.aros.api.area;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.usecases.area.GetAreaUseCase;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAreaUseCaseTest {

    @Mock
    private AreaRepository repository;

    @InjectMocks
    private GetAreaUseCase useCase;

    @Test
    void whenAreaExists_thenReturnAreaSuccessfully() {
        // Arrange
        GetAreaInput input = new GetAreaInput("Cocina");
        Area area = new Area();
        area.setId(1L);
        area.setName("Cocina");

        when(repository.existsByName("Cocina")).thenReturn(true);
        when(repository.getAreaByName("Cocina")).thenReturn(Optional.of(area));

        GetAreaOutput result = useCase.execute(input.name());

        assertNotNull(result);
        assertEquals("Cocina", result.name());
        verify(repository, times(1)).existsByName("Cocina");
        verify(repository, times(1)).getAreaByName("Cocina");
    }

    @Test
    void whenAreaDoesNotExist_thenThrowException() {
        GetAreaInput input = new GetAreaInput("Inexistente");

        when(repository.existsByName("Inexistente")).thenReturn(false);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> useCase.execute(input.name()));

        assertEquals("Are no exist", exception.getMessage());
        verify(repository, times(1)).existsByName("Inexistente");
        verify(repository, never()).getAreaByName(anyString());
    }

}
