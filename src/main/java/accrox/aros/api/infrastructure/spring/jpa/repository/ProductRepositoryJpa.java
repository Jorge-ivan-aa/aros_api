package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, Long> {
    public boolean existsByName(String name);

    public Optional<ProductEntity> findByName(String name);
}
