package accrox.aros.api.area;

import accrox.aros.api.application.dto.area.DeleteAreaInput;
import accrox.aros.api.application.usecases.area.DeleteAreaUseCase;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAreaUseCaseTest {

    @Mock
    private AreaRepository repository;

    @InjectMocks
    private DeleteAreaUseCase useCase;

    @Test
    void whenAreaExists_thenDeleteSuccessfully() {

        DeleteAreaInput input = new DeleteAreaInput("Cocina");

        Area area = new Area();
        area.setId(1L);
        area.setName("Cocina");

        when(repository.existsByName("Cocina")).thenReturn(true);
        when(repository.getAreaByName("Cocina")).thenReturn(Optional.of(area));

        useCase.execute(input);

        verify(repository, times(1)).existsByName("Cocina");
        verify(repository, times(1)).getAreaByName("Cocina");
        verify(repository, times(1)).deleteArea(1L);
    }

}
