package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.RefreshTokenEntity;

public interface RefreshTokenRepositoryJpa extends CrudRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByHash(String hash);
}
