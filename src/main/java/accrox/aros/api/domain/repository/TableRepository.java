package accrox.aros.api.domain.repository;

import java.util.List;

import accrox.aros.api.domain.model.Table;

public interface TableRepository {
    List<Table> findAllTables();

    public void createTable(Table table);

    public void deleteTable(Long id);

    public boolean existsById(Long id);
}