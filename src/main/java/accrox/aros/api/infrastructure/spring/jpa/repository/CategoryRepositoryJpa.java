package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;

public interface CategoryRepositoryJpa extends CrudRepository<CategoryEntity, Long> {
    public boolean existsByName(String name);
}
