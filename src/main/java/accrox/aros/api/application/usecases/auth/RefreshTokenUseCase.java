package accrox.aros.api.application.usecases.auth;

import accrox.aros.api.application.dto.auth.AuthOutput;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.TokenService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshTokenUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
        RefreshTokenUseCase.class
    );

    private RefreshTokenRepository tokenRepository;
    private TokenService tokenService;

    private EntityManager manager;

    private Integer refreshTokenDuration;

    public RefreshTokenUseCase(
        RefreshTokenRepository tokenRepository,
        UserRepository userRepository,
        TokenService tokenService,
        EntityManager manager,
        Integer refreshTokenDuration
    ) {
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.manager = manager;
        this.refreshTokenDuration = refreshTokenDuration;
    }

    public AuthOutput execute(String refresh_token)
        throws InvalidTokenException {
        logger.debug("Executing refresh token use case");

        RefreshToken refreshToken = this.tokenRepository.findByHash(
            refresh_token
        ).orElseThrow(() -> {
            logger.warn("Refresh token not found in repository");
            return new InvalidTokenException();
        });

        if (refreshToken.getRevokedAt() != null) {
            logger.warn(
                "Refresh token was revoked at: {}",
                refreshToken.getRevokedAt()
            );
            throw new InvalidTokenException();
        }

        // Check token expiration
        if (
            refreshToken.getCreatedAt() != null && refreshTokenDuration != null
        ) {
            LocalDateTime expirationTime = refreshToken
                .getCreatedAt()
                .plusDays(refreshTokenDuration);
            if (LocalDateTime.now().isAfter(expirationTime)) {
                logger.warn(
                    "Refresh token expired at: {}, current time: {}",
                    expirationTime,
                    LocalDateTime.now()
                );
                throw new InvalidTokenException();
            }
        }

        logger.debug(
            "Refresh token validated successfully for user: {}",
            refreshToken.getUserDocument()
        );

        manager.clear();

        String userDocument = refreshToken.getUserDocument();
        logger.debug(
            "Revoking old refresh token with ID: {}",
            refreshToken.getId()
        );

        this.tokenRepository.revokeToken(refreshToken.getId());

        String newRefreshToken = this.tokenService.generateRefreshToken(
            userDocument
        );
        String newAccessToken = this.tokenService.generateAccessToken(
            userDocument
        );

        logger.debug("Generated new tokens for user: {}", userDocument);

        this.tokenRepository.create(
            new RefreshToken(
                null,
                newRefreshToken,
                LocalDateTime.now(),
                null,
                userDocument
            )
        );

        logger.debug("Token refresh completed successfully");
        return new AuthOutput(newRefreshToken, newAccessToken);
    }
}
