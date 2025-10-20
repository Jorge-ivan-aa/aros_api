package accrox.aros.api.area;

import accrox.aros.api.application.dto.area.SaveAreaInput;
import accrox.aros.api.application.exceptions.ValidationException;
import accrox.aros.api.application.usecases.area.SaveAreaUseCase;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveAreaUseCaseTest {

    @Mock
    private AreaRepository repository;

    @InjectMocks
    private SaveAreaUseCase useCase;

    @Test
    void whenValidInput_thenSaveAreaSuccessfully() throws ValidationException {

        SaveAreaInput input = new SaveAreaInput("newArea");

        when(repository.existsByName(input.name())).thenReturn(false);

        ArgumentCaptor<Area> areaCaptor = ArgumentCaptor.forClass(Area.class);

        useCase.execute(input);

        verify(repository, times(1)).saveArea(areaCaptor.capture());

        Area savedArea = areaCaptor.getValue();

        assertEquals(input.name(), savedArea.getName());

        assertDoesNotThrow(() -> useCase.execute(input));


    }

}
