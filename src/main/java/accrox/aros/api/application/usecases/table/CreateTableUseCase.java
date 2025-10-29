package accrox.aros.api.application.usecases.table;

import accrox.aros.api.application.dto.table.CreateTableDto;
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
        java.util.regex.Pattern digits = java.util.regex.Pattern.compile("\\d+");
        for (Table t : tables) {
            String name = t.getName();
            if (name == null)
                continue;
            java.util.regex.Matcher m = digits.matcher(name);
            if (m.find()) {
                try {
                    int num = Integer.parseInt(m.group());
                    if (num > 0)
                        usedNumbers.add(num);
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        }
        int nextNumber = 1;
        while (usedNumbers.contains(nextNumber))
            nextNumber++;
        Table table = new Table();
        table.setName("Mesa " + nextNumber);
        repository.createTable(table);
    }
}
