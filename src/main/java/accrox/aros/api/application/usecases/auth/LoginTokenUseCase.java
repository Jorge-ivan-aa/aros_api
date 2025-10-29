package accrox.aros.api.application.usecases.auth;

import accrox.aros.api.application.dto.auth.AuthInput;
import accrox.aros.api.application.dto.auth.AuthOutput;
import accrox.aros.api.application.exceptions.auth.InsecurePasswordException;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.domain.model.RefreshToken;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.RefreshTokenRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.PasswordService;
import accrox.aros.api.domain.service.TokenService;
import java.time.LocalDateTime;

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
        Integer refreshTokenDuration
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.adminAuthService = adminAuthService;
        this.tokenRepository = tokenRepository;
        this.passwordService = passwordService;
    }

    public AuthOutput execute(AuthInput request)
        throws InvalidCredentialsException, InsecurePasswordException {
        User user;
        if (adminAuthService.isAdminCredentials(request.document())) {
            user = adminAuthService.getUser();
        } else {
            user = this.userRepository.findByEmail(
                request.document()
            ).orElseThrow(() ->
                new InvalidCredentialsException("User not found")
            );
        }

        if (!user.passwordMatches(request.password(), this.passwordService)) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String refreshToken = this.tokenService.generateRefreshToken(
            user.getEmail()
        );
        String accessToken = this.tokenService.generateAccessToken(
            user.getEmail()
        );

        this.tokenRepository.create(
            new RefreshToken(
                null,
                refreshToken,
                LocalDateTime.now(),
                user.getEmail()
            )
        );

        return new AuthOutput(refreshToken, accessToken);
    }
}
