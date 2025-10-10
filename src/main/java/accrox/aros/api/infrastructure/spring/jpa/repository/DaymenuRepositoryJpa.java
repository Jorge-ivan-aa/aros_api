package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;

public interface DaymenuRepositoryJpa extends CrudRepository<DaymenuEntity, Long> {}
