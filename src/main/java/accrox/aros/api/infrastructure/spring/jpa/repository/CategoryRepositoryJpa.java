package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;

public interface CategoryRepositoryJpa extends CrudRepository<CategoryEntity, Long> {
    public boolean existsByName(String name);

    @Query("SELECT COUNT(c) = :size FROM CategoryEntity c WHERE c.id IN :ids")
    public boolean existsAllByIdIn(Set<Long> ids, long size);
}
