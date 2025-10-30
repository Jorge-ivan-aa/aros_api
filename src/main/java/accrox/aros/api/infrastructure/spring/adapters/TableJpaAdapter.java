package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.repository.TableRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.TableEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.TableRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.TableJpaMapper;

@Repository
public class TableJpaAdapter implements TableRepository {
    @Autowired
    private TableRepositoryJpa tableRepositoryJpa;

    @Override
    public java.util.List<Table> findAllTables() {
        java.util.List<Table> tables = new java.util.ArrayList<>();
        for (TableEntity entity : tableRepositoryJpa.findAll()) {
            tables.add(TableJpaMapper.toDomain(entity, null));
        }
        return tables;
    }

    @Override
    public void createTable(Table table) {
        TableEntity entity = TableJpaMapper.toEntity(table, null);
        this.tableRepositoryJpa.save(entity);
    }

    @Override
    public void deleteTable(Long id) {
        this.tableRepositoryJpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.tableRepositoryJpa.existsById(id);
    }
}
