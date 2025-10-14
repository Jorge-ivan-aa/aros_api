package accrox.aros.api.infrastructure.spring.adapters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.UserRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.UserJpaMapper;

@Repository
public class UserJpaAdapter implements UserRepository {

    private static Logger logger = LoggerFactory.getLogger(UserJpaAdapter.class);

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public Optional<User> findById(Long id) {
        logger.info("Validating user credentials for: {}", id);
        return userRepositoryJpa.findById(id)
                .map(entity -> UserJpaMapper.toDomain(entity, null, null));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Validating user credentials for: {}", email);
        return userRepositoryJpa.findByEmail(email)
                .map(entity -> UserJpaMapper.toDomain(entity, null, null));
    }

}