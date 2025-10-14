package accrox.aros.api.application.usecases.auth;

import java.time.LocalDateTime;

import accrox.aros.api.application.dto.auth.AuthRequestDto;
import accrox.aros.api.application.dto.auth.AuthTokenReponseDto;
import accrox.aros.api.application.exceptions.auth.InsecurePasswordException;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.PasswordService;
import accrox.aros.api.domain.service.TokenService;

public class LoginTokenUseCase {
    private TokenService tokenService;

    private UserRepository userRepository;

    private AdminAuthService adminAuthService;

    private RefreshTokenRepository tokenRepository;

    private PasswordService passwordService;

    public LoginTokenUseCase(
            TokenService tokenService,
            UserRepository userRepository,
            AdminAuthService adminAuthService,
            RefreshTokenRepository tokenRepository,
            PasswordService passwordService,
            Integer refreshTokenDuration) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.adminAuthService = adminAuthService;
        this.tokenRepository = tokenRepository;
        this.passwordService = passwordService;
    }

    public AuthTokenReponseDto execute(AuthRequestDto request)
            throws InvalidCredentialsException, InsecurePasswordException {

        User user;
        if (adminAuthService.isAdminCredentials(request.getEmail())) {
            user = adminAuthService.getUser();

        } else {
            user = this.userRepository
                    .findByEmail(request.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        }

        if (!user.passwordMatches(request.getPassword(), this.passwordService)) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String refreshToken = this.tokenService.generateRefreshToken(user.getEmail());
        String accessToken = this.tokenService.generateAccessToken(user.getEmail());

        this.tokenRepository.create(new RefreshToken(
                null,
                refreshToken,
                LocalDateTime.now(),
                user.getEmail()));

        return new AuthTokenReponseDto(refreshToken, accessToken);
    }
}
