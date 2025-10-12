package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ClientOrderEntity;

public interface ClientOrderRepositoryJpa extends CrudRepository<ClientOrderEntity, Long> {}
