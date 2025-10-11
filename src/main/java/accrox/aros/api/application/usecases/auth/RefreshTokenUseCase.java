package accrox.aros.api.application.usecases.auth;

import java.time.LocalDateTime;

import accrox.aros.api.application.dto.auth.AuthTokenReponseDto;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.TokenService;
import jakarta.persistence.EntityManager;

public class RefreshTokenUseCase {
    private RefreshTokenRepository tokenRepository;
    private TokenService tokenService;

    private EntityManager manager;

    public RefreshTokenUseCase(
            RefreshTokenRepository tokenRepository,
            UserRepository userRepository,
            TokenService tokenService,
            EntityManager manager) {
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.manager = manager;
    }

    public AuthTokenReponseDto execute(String token) throws InvalidTokenException {
        RefreshToken refreshToken = this.tokenRepository.findByHash(token)
                .orElseThrow(() -> new InvalidTokenException());

        if (refreshToken.getRevokedAt() != null
                && refreshToken.getRevokedAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException();
        }

        manager.clear();

        User user = refreshToken.getUser();

        this.tokenRepository.revokeToken(refreshToken.getId());

        String newRefreshToken = this.tokenService.generateRefreshToken(user);
        String newAccessToken = this.tokenService.generateAccessToken(user);

        this.tokenRepository.create(new RefreshToken(
                null,
                newRefreshToken,
                LocalDateTime.now(),
                user));

        return new AuthTokenReponseDto(newRefreshToken, newAccessToken);
    }
}
