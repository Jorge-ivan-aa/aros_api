package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;

import java.util.List;
import java.util.Optional;

public interface AreaRepositoryJpa extends CrudRepository<AreaEntity, Long> {
    boolean existsByName(String name);

    Optional<AreaEntity> getByName(String name);
}
