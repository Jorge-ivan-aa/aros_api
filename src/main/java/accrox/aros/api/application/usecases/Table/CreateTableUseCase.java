package accrox.aros.api.application.usecases.Table;

import accrox.aros.api.application.dto.Table.CreateTableDto;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.repository.TableRepository;

public class CreateTableUseCase {
    private TableRepository repository;

    public CreateTableUseCase(TableRepository repository) {
        this.repository = repository;
    }
    public void execute(CreateTableDto dto) {
        java.util.List<Table> tables = repository.findAllTables();
        java.util.Set<Integer> usedNumbers = new java.util.HashSet<>();
        for (Table t : tables) {
            try {
                int num = Integer.parseInt(t.getName());
                if (num > 0) {
                    usedNumbers.add(num);
                }
            } catch (NumberFormatException e) {
                // Ignorar nombres no numéricos
            }
        }
        int nextNumber = 1;
        while (usedNumbers.contains(nextNumber)) {
            nextNumber++;
        }
        Table table = new Table();
        table.setName(String.valueOf(nextNumber));
        repository.createTable(table);
    }
}
