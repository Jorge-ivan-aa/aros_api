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
            System.out.println(
                "DEBUG: LoginTokenUseCase - Admin credentials detected for document: " +
                    request.document()
            );
            user = adminAuthService.getUser();
            System.out.println(
                "DEBUG: LoginTokenUseCase - Admin user created with document: " +
                    user.getDocument()
            );
        } else {
            System.out.println(
                "DEBUG: LoginTokenUseCase - Regular user login for document: " +
                    request.document()
            );
            user = this.userRepository.findByDocument(
                request.document()
            ).orElseThrow(() ->
                new InvalidCredentialsException("User not found")
            );
            System.out.println(
                "DEBUG: LoginTokenUseCase - Regular user found with document: " +
                    user.getDocument()
            );
        }

        if (!user.passwordMatches(request.password(), this.passwordService)) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String userDocument = user.getDocument();
        System.out.println(
            "DEBUG: LoginTokenUseCase - Generating tokens for document: " +
                userDocument
        );

        String refreshToken = this.tokenService.generateRefreshToken(
            userDocument
        );
        String accessToken = this.tokenService.generateAccessToken(
            userDocument
        );

        RefreshToken refreshTokenEntity = new RefreshToken(
            null,
            refreshToken,
            LocalDateTime.now(),
            user.getDocument()
        );
        System.out.println(
            "DEBUG: LoginTokenUseCase - Creating refresh token with document: " +
                refreshTokenEntity.getUserDocument()
        );

        this.tokenRepository.create(refreshTokenEntity);

        return new AuthOutput(refreshToken, accessToken);
    }
}
