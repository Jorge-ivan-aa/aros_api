package accrox.aros.api.infrastructure.spring.adapters;

import java.util.Optional;

import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;
import jakarta.transaction.Transactional;
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
    public Optional<User> findByDocument(String document) {
        return userRepositoryJpa.findByDocument(document)
                .map(entity -> UserJpaMapper.toDomain(entity, null, null));
    }

    @Override
    public void save(User user) {
        UserEntity toSave = UserJpaMapper.toEntity(user, null, null);
        this.userRepositoryJpa.save(toSave);
    }

    @Override
    @Transactional
    public void deleteUserByDocument(String document) {
        this.userRepositoryJpa.deleteByDocument(document);
    }

    @Override
    @Transactional
    public void update(User user) {
        this.userRepositoryJpa.save(UserJpaMapper.toEntity(user, null, null));
    }


}