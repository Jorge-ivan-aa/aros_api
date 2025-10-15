package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;

public interface AreaRepositoryJpa extends CrudRepository<AreaEntity, Long> {
    boolean existsByName(String name);

    Optional<AreaEntity> getByName(String name);
}
