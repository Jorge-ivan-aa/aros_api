package accrox.aros.api.application.usecases.table;

import accrox.aros.api.application.dto.table.UpdateTablesCountDto;
import accrox.aros.api.application.dto.table.CreateTableDto;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.repository.TableRepository;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTablesCountUseCase {

    private final TableRepository repository;
    private final CreateTableUseCase createTableUseCase;

    public UpdateTablesCountUseCase(TableRepository repository, CreateTableUseCase createTableUseCase) {
        this.repository = repository;
        this.createTableUseCase = createTableUseCase;
    }

    public void execute(UpdateTablesCountDto dto) {
        int desired = dto.total();
        if (desired < 0)
            throw new IllegalArgumentException("total must be >= 0");

        List<Table> tables = repository.findAllTables();
        int current = tables.size();
        if (desired == current)
            return;

        if (desired > current) {
            int toCreate = desired - current;
            for (int i = 0; i < toCreate; i++) {
                // delegate to createTableUseCase which will assign the correct name
                this.createTableUseCase.execute(new CreateTableDto(null));
            }
            return;
        }

        // desired < current -> delete highest-numbered tables
        int toDelete = current - desired;
        Pattern digits = Pattern.compile("\\d+");
        tables.stream()
                .sorted(Comparator.<Table>comparingInt(t -> {
                    String name = t.getName();
                    if (name == null)
                        return Integer.MIN_VALUE;
                    Matcher m = digits.matcher(name);
                    if (m.find()) {
                        try {
                            return Integer.parseInt(m.group());
                        } catch (NumberFormatException e) {
                            return Integer.MIN_VALUE;
                        }
                    }
                    return Integer.MIN_VALUE;
                }).reversed())
                .limit(toDelete)
                .forEach(t -> {
                    Long id = t.getId();
                    if (id != null && repository.existsById(id)) {
                        repository.deleteTable(id);
                    }
                });
    }
}
