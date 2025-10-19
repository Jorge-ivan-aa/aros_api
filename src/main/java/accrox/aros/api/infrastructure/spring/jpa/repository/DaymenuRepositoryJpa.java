package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;

public interface DaymenuRepositoryJpa extends CrudRepository<DaymenuEntity, Long> {
    public boolean existsByName(String name);

    @NativeQuery(value = "SELECT id FROM daymenus d WHERE d.id IN :ids")
    public Collection<Long> findIdsIn(Collection<Long> ids);
}
