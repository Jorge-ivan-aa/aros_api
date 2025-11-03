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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTokenUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
        LoginTokenUseCase.class
    );

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
        logger.debug("Login attempt for user: {}", request.document());

        User user;
        if (adminAuthService.isAdminCredentials(request.document())) {
            logger.debug(
                "Admin credentials detected for user: {}",
                request.document()
            );
            user = adminAuthService.getUser();
        } else {
            logger.debug(
                "Regular user login attempt for: {}",
                request.document()
            );
            user = this.userRepository.findByDocument(
                request.document()
            ).orElseThrow(() -> {
                logger.warn("User not found: {}", request.document());
                return new InvalidCredentialsException("User not found");
            });
        }

        if (!user.passwordMatches(request.password(), this.passwordService)) {
            logger.warn("Invalid password for user: {}", request.document());
            throw new InvalidCredentialsException("Invalid password");
        }

        String userDocument = user.getDocument();
        logger.debug("Generating tokens for user: {}", userDocument);

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
            null,
            user.getDocument()
        );

        this.tokenRepository.create(refreshTokenEntity);
        logger.debug(
            "Refresh token stored successfully for user: {}",
            userDocument
        );

        logger.debug("Login successful for user: {}", userDocument);
        return new AuthOutput(refreshToken, accessToken);
    }
}
