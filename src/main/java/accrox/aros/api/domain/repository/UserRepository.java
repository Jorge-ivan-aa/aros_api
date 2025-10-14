package accrox.aros.api.domain.repository;

import java.util.Collection;
import java.util.Optional;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByDocument(String document);

    void save(User user);

    void updateUserArea(User user , Collection<AreaEntity> areas);

    void deleteUserByDocument(String document);

    void update(User user);

}