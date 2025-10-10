package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;

public interface AreaRepositoryJpa extends CrudRepository<AreaEntity, Long> {}
