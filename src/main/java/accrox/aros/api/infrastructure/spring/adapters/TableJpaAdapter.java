package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.TableRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.TableRepositoryJpa;

@Repository
public class TableJpaAdapter implements TableRepository {

    @Autowired
    private TableRepositoryJpa tableRepositoryJpa;

}
