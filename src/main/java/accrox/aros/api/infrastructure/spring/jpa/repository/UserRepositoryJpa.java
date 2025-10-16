package accrox.aros.api.infrastructure.spring.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;
import org.springframework.data.repository.query.Param;

public interface UserRepositoryJpa extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByDocument(String document);

    Optional<UserEntity> deleteByDocument(String document);

    Optional<UserEntity> findByEmail(String email);

}