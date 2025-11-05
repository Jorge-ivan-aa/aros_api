package accrox.aros.api.application.usecases.table;

import accrox.aros.api.application.dto.table.TablesAmountOutput;
import accrox.aros.api.domain.repository.TableRepository;

public class GetTablesAmountUseCase {

    private TableRepository repository;

    public GetTablesAmountUseCase(TableRepository repository) {
        this.repository = repository;
    }

    public TablesAmountOutput execute() {
        Long amount = (long) repository.findAllTables().size();
        return new TablesAmountOutput(amount);
    }
}
