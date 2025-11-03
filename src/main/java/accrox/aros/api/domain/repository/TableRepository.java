package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Table;
import java.util.List;

public interface TableRepository {
    List<Table> findAllTables();

    List<Table> findAllTablesWithOrders();

    public void createTable(Table table);

    public void deleteTable(Long id);

    public boolean existsById(Long id);
}
