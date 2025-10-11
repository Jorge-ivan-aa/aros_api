package accrox.aros.api.infrastructure.spring.adapters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.UserRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.UserJpaMapper;

@Repository
public class UserJpaAdapter implements UserRepository {

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(UserJpaMapper.toDomain(userRepositoryJpa.findById(id).get(), null, null));
    }

    @Override
    public Optional<User> findByEmail(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }
}