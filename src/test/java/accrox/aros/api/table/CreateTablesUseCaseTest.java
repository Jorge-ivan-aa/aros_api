package accrox.aros.api.table;

import accrox.aros.api.application.dto.table.CreateTablesDto;
import accrox.aros.api.application.usecases.table.CreateTablesUseCase;
import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateTablesUseCaseTest {

    @Mock
    private CreateTableUseCase createTableUseCase;

    @InjectMocks
    private CreateTablesUseCase createTablesUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_count_is_three_calls_create_three_times() {
        CreateTablesDto dto = new CreateTablesDto(3);

        createTablesUseCase.execute(dto);

        verify(createTableUseCase, times(3)).execute(any());
    }

    @Test
    public void when_count_is_zero_throws_exception() {
        CreateTablesDto dto = new CreateTablesDto(0);

        assertThrows(IllegalArgumentException.class, () -> createTablesUseCase.execute(dto));
    }
}
