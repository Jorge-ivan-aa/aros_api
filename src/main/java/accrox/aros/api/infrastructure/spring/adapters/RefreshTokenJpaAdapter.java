package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.RefreshTokenRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.RefreshTokenJpaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenJpaAdapter implements RefreshTokenRepository {

    @Autowired
    private RefreshTokenRepositoryJpa refreshTokenRepositoryJpa;

    private static final Logger logger = LoggerFactory.getLogger(
        RefreshTokenJpaAdapter.class
    );

    @Override
    public Optional<RefreshToken> findById(Long id) {
        logger.info("Searching refresh token by ID: {}", id);
        return refreshTokenRepositoryJpa
            .findById(id)
            .map(entity -> RefreshTokenJpaMapper.toDomain(entity, null));
    }

    @Override
    public Optional<RefreshToken> findByHash(String hash) {
        logger.info("Searching refresh token by hash: {}", hash);
        return refreshTokenRepositoryJpa
            .findByHash(hash)
            .map(entity -> RefreshTokenJpaMapper.toDomain(entity, null));
    }

    @Override
    public RefreshToken create(RefreshToken token) {
        logger.info(
            "Creating refresh token for user: {}",
            token.getUserDocument()
        );
        return RefreshTokenJpaMapper.toDomain(
            this.refreshTokenRepositoryJpa.save(
                RefreshTokenJpaMapper.toEntity(token, token.getUserDocument())
            ),
            token.getUserDocument()
        );
    }

    @Override
    public void revokeToken(Long id) {
        logger.info("Revoking refresh token with ID: {}", id);
        refreshTokenRepositoryJpa
            .findById(id)
            .ifPresent(token -> {
                token.setRevokedAt(java.time.LocalDateTime.now());
                refreshTokenRepositoryJpa.save(token);
            });
    }
}
