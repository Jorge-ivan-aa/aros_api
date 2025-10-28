package accrox.aros.api.application.usecases.Table;

import accrox.aros.api.application.dto.Table.CreateTablesDto;
import accrox.aros.api.application.dto.Table.CreateTableDto;

public class CreateTablesUseCase {
    private CreateTableUseCase createTableUseCase;

    public CreateTablesUseCase(CreateTableUseCase createTableUseCase) {
        this.createTableUseCase = createTableUseCase;
    }

    public void execute(CreateTablesDto dto) {
        int count = dto.getCount();
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }
        for (int i = 0; i < count; i++) {
            // CreateTableUseCase ignores the DTO name and assigns the next available number
            this.createTableUseCase.execute(new CreateTableDto(null));
        }
    }
}
