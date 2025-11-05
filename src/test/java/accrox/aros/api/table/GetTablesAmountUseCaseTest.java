package accrox.aros.api.table;

import accrox.aros.api.application.dto.table.TablesAmountOutput;
import accrox.aros.api.application.usecases.table.GetTablesAmountUseCase;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GetTablesAmountUseCaseTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private GetTablesAmountUseCase getTablesAmountUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_no_tables_exist_then_return_zero() {
        when(tableRepository.findAllTables()).thenReturn(Collections.emptyList());

        TablesAmountOutput result = getTablesAmountUseCase.execute();

        assertEquals(0L, result.amount());
        verify(tableRepository).findAllTables();
    }

    @Test
    void when_one_table_exists_then_return_one() {
        Table table = new Table(1L, "Mesa 1", null);
        when(tableRepository.findAllTables()).thenReturn(List.of(table));

        TablesAmountOutput result = getTablesAmountUseCase.execute();

        assertEquals(1L, result.amount());
        verify(tableRepository).findAllTables();
    }

    @Test
    void when_multiple_tables_exist_then_return_correct_count() {
        Table table1 = new Table(1L, "Mesa 1", null);
        Table table2 = new Table(2L, "Mesa 2", null);
        Table table3 = new Table(3L, "Mesa 3", null);
        Table table4 = new Table(4L, "Mesa 4", null);
        Table table5 = new Table(5L, "Mesa 5", null);

        when(tableRepository.findAllTables()).thenReturn(
                List.of(table1, table2, table3, table4, table5));

        TablesAmountOutput result = getTablesAmountUseCase.execute();

        assertEquals(5L, result.amount());
        verify(tableRepository).findAllTables();
    }
}
