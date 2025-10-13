package accrox.aros.api.domain.repository;

import java.util.Optional;
import accrox.aros.api.domain.model.User;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByDocument(String document);

    void save(User user);

    void deleteUserByDocument(String document);

}