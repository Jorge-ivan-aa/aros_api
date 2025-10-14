package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.auth.LoginTokenUseCase;
import accrox.aros.api.application.usecases.auth.RefreshTokenUseCase;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.PasswordService;
import accrox.aros.api.domain.service.TokenService;
import jakarta.persistence.EntityManager;

@Configuration
public class UseCaseBeanConfig {

    @Bean
    public LoginTokenUseCase authUseCase(
            AdminAuthService adminAuthService,
            UserRepository userRepository,
            PasswordService passwordService,
            TokenService tokenService,
            RefreshTokenRepository tokenRepository,
            @Value("${security.token.lifetime}") Integer tokenLifeTime) {
        return new LoginTokenUseCase(
                tokenService,
                userRepository,
                adminAuthService,
                tokenRepository,
                passwordService,
                tokenLifeTime);
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(
            UserRepository workerRespository,
            RefreshTokenRepository refreshTokenRepository,
            TokenService tokenService,
            EntityManager manager) {
        return new RefreshTokenUseCase(
                refreshTokenRepository,
                workerRespository,
                tokenService,
                manager);
    }
}