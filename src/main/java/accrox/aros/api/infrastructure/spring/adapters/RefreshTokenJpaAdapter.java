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
        logger.debug("Searching refresh token by ID: {}", id);
        Optional<RefreshToken> result = refreshTokenRepositoryJpa
            .findById(id)
            .map(entity ->
                RefreshTokenJpaMapper.toDomain(entity, entity.getUserDocument())
            );

        if (result.isPresent()) {
            logger.debug("Refresh token found by ID: {}", id);
        } else {
            logger.debug("Refresh token not found by ID: {}", id);
        }

        return result;
    }

    @Override
    public Optional<RefreshToken> findByHash(String hash) {
        logger.debug(
            "Searching refresh token by hash, hash length: {}",
            hash.length()
        );
        Optional<RefreshToken> result = refreshTokenRepositoryJpa
            .findByHash(hash)
            .map(entity ->
                RefreshTokenJpaMapper.toDomain(entity, entity.getUserDocument())
            );

        if (result.isPresent()) {
            logger.debug("Refresh token found by hash");
            RefreshToken token = result.get();
            logger.debug(
                "Token ID: {}, User: {}, Revoked: {}",
                token.getId(),
                token.getUserDocument(),
                token.getRevokedAt()
            );
        } else {
            logger.warn("Refresh token not found by hash");
        }

        return result;
    }

    @Override
    public RefreshToken create(RefreshToken token) {
        logger.debug(
            "Creating refresh token for user: {}",
            token.getUserDocument()
        );
        RefreshToken result = RefreshTokenJpaMapper.toDomain(
            this.refreshTokenRepositoryJpa.save(
                RefreshTokenJpaMapper.toEntity(token, token.getUserDocument())
            ),
            token.getUserDocument()
        );

        logger.debug(
            "Refresh token created successfully, ID: {}",
            result.getId()
        );
        return result;
    }

    @Override
    public void revokeToken(Long id) {
        logger.debug("Revoking refresh token with ID: {}", id);
        refreshTokenRepositoryJpa
            .findById(id)
            .ifPresent(token -> {
                logger.debug(
                    "Found token to revoke, current revokedAt: {}",
                    token.getRevokedAt()
                );
                token.setRevokedAt(java.time.LocalDateTime.now());
                refreshTokenRepositoryJpa.save(token);
                logger.debug(
                    "Token revoked successfully, new revokedAt: {}",
                    token.getRevokedAt()
                );
            });
    }
}
