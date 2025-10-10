package accrox.aros.api.infrastructure.spring.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;

public interface UserRepositoryJpa extends CrudRepository<UserEntity, Long> {}
