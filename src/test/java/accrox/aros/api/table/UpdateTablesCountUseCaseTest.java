package accrox.aros.api.table;

import accrox.aros.api.application.dto.table.CreateTableDto;
import accrox.aros.api.application.dto.table.UpdateTablesCountDto;
import accrox.aros.api.application.usecases.table.UpdateTablesCountUseCase;
import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateTablesCountUseCaseTest {

    @Mock
    private TableRepository repository;

    @Mock
    private CreateTableUseCase createTableUseCase;

    @InjectMocks
    private UpdateTablesCountUseCase useCase;

    @Test
    void whenDesiredEqualsCurrent_thenDoNothing() {
        List<Table> tables = new java.util.ArrayList<>();
        Table t1 = new Table();
        t1.setId(1L);
        t1.setName("Table 1");
        tables.add(t1);

        when(repository.findAllTables()).thenReturn(tables);

        useCase.execute(new UpdateTablesCountDto(1));

        verify(createTableUseCase, times(0)).execute(any(CreateTableDto.class));
        verify(repository, never()).deleteTable(anyLong());
    }

    @Test
    void whenDesiredGreaterThanCurrent_thenCreateNewTables() {
        List<Table> tables = new java.util.ArrayList<>();
        Table t1 = new Table();
        t1.setId(1L);
        t1.setName("Table 1");
        tables.add(t1);

        when(repository.findAllTables()).thenReturn(tables);

        useCase.execute(new UpdateTablesCountDto(3));

        // should create two new tables
        verify(createTableUseCase, times(2)).execute(any(CreateTableDto.class));
    }

    @Test
    void whenDesiredLessThanCurrent_thenDeleteHighestNumbered() {
        List<Table> tables = new java.util.ArrayList<>();

        Table t1 = new Table();
        t1.setId(1L);
        t1.setName("Table 1");

        Table t2 = new Table();
        t2.setId(2L);
        t2.setName("Table 2");

        Table t3 = new Table();
        t3.setId(3L);
        t3.setName("Table 10");

        tables.add(t1);
        tables.add(t2);
        tables.add(t3);

        when(repository.findAllTables()).thenReturn(tables);
        lenient().when(repository.existsById(3L)).thenReturn(true);
        lenient().when(repository.existsById(2L)).thenReturn(true);
        lenient().when(repository.existsById(1L)).thenReturn(true);

        // reduce to 1 table -> should delete two highest numbered (10 and 2)
        useCase.execute(new UpdateTablesCountDto(1));

        verify(repository, times(1)).deleteTable(3L);
        verify(repository, times(1)).deleteTable(2L);
        verify(repository, never()).deleteTable(1L);
    }

    @Test
    void whenDesiredIsNegative_thenThrow() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new UpdateTablesCountDto(-1)));
    }

}
