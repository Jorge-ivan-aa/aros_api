package accrox.aros.api.application.usecases.table;

import accrox.aros.api.application.dto.table.CreateTablesDto;
import accrox.aros.api.application.dto.table.CreateTableDto;

public class CreateTablesUseCase {
    private final CreateTableUseCase createTableUseCase;

    public CreateTablesUseCase(CreateTableUseCase createTableUseCase) {
        this.createTableUseCase = createTableUseCase;
    }

    public void execute(CreateTablesDto dto) {
        int count = dto.count();
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }
        for (int i = 0; i < count; i++) {
            // CreateTableUseCase ignores the DTO name and assigns the next available number
            this.createTableUseCase.execute(new CreateTableDto(null));
        }
    }
}
