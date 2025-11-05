package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;

public interface AreaRepositoryJpa extends CrudRepository<AreaEntity, Long> {
    boolean existsByName(String name);

    Optional<AreaEntity> getByName(String name);

    @Query("SELECT COUNT(a) = :size FROM AreaEntity a WHERE a.id IN :ids")
    public boolean existsAllByIdIn(Set<Long> ids, long size);
}
