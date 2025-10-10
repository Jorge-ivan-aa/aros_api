package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, Long> {}
