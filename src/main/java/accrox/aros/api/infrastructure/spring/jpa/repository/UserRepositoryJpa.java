package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryJpa extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByDocument(String document);


}

