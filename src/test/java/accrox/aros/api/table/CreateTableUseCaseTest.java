package accrox.aros.api.table;

import accrox.aros.api.application.dto.table.CreateTableDto;
import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateTableUseCaseTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private CreateTableUseCase createTableUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_no_tables_exist_then_create_table_with_name_1() {
        CreateTableDto dto = new CreateTableDto();
        when(tableRepository.findAllTables()).thenReturn(Collections.emptyList());

        createTableUseCase.execute(dto);

        ArgumentCaptor<Table> captor = ArgumentCaptor.forClass(Table.class);
        verify(tableRepository).createTable(captor.capture());

        Table createdTable = captor.getValue();
        assertEquals("1", createdTable.getName());
    }

    @Test
    void when_tables_with_numeric_names_exist_then_create_table_with_next_number() {

        Table t1 = new Table(1L, "1", null);
        Table t2 = new Table(2L, "2", null);
        Table t3 = new Table(3L, "4", null); // Falta el 3

        when(tableRepository.findAllTables()).thenReturn(List.of(t1, t2, t3));

        CreateTableDto dto = new CreateTableDto();

        createTableUseCase.execute(dto);

        ArgumentCaptor<Table> captor = ArgumentCaptor.forClass(Table.class);
        verify(tableRepository).createTable(captor.capture());

        Table createdTable = captor.getValue();
        assertEquals("3", createdTable.getName());
    }
}