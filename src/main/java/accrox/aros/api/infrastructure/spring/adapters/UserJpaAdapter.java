package accrox.aros.api.infrastructure.spring.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.UserEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.UserRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.UserJpaMapper;
import jakarta.transaction.Transactional;

@Repository
public class UserJpaAdapter implements UserRepository {

    private static Logger logger = LoggerFactory.getLogger(
            UserJpaAdapter.class);

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(
                UserJpaMapper.toDomain(
                        userRepositoryJpa.findById(id).get(),
                        null,
                        null,
                        null));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Validating user credentials for: {}", email);
        return userRepositoryJpa
                .findByEmail(email)
                .map(entity -> UserJpaMapper.toDomain(entity, null, null, null));
    }

    @Override
    @Transactional
    public Optional<User> findByDocument(String document) {
        logger.info("Document received: {}", document);
        return userRepositoryJpa
                .findByDocument(document)
                .map(entity -> UserJpaMapper.toDomain(entity, entity.getAreas(), null, null));
    }

    @Override
    @Transactional
    public List<User> findAll() {
        Iterable<UserEntity> entities = userRepositoryJpa.findAll();

        List<User> users = new ArrayList<>();
        for (UserEntity entity : entities) {
            users.add(UserJpaMapper.toDomain(entity, entity.getAreas(), null, null));
        }

        return users;
    }

    @Override
    public void save(User user) {
        UserEntity toSave = UserJpaMapper.toEntity(user, user.getAreas(), null, null);
        this.userRepositoryJpa.save(toSave);
    }

    /*
     * @Override
     * public void updateUserArea(User user , Collection<AreaEntity> areas) {
     * 
     * this.userRepositoryJpa.save(UserJpaMapper.toEntity(user, user.getAreas()
     * null));
     * 
     * }
     */

    @Override
    @Transactional
    public void deleteUserByDocument(String document) {
        this.userRepositoryJpa.deleteByDocument(document);
    }

    @Override
    @Transactional
    public void update(User user) {
        System.out.println("-----------------------------------");
        user.getAreas().forEach(a -> {
            System.out.println(a.getName());
        });
        System.out.println("-----------------------------------");

        this.userRepositoryJpa.save(UserJpaMapper.toEntity(user, user.getAreas(), null, null));
    }
}
