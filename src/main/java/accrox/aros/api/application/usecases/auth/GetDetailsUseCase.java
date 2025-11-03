package accrox.aros.api.application.usecases.auth;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.dto.auth.AuthDetailsOutput;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.TokenService;

public class GetDetailsUseCase {

    private static final Logger logger = LoggerFactory.getLogger(
        GetDetailsUseCase.class
    );

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AdminAuthService adminAuthService;

    public GetDetailsUseCase(
        TokenService tokenService,
        UserRepository userRepository,
        AdminAuthService adminAuthService
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.adminAuthService = adminAuthService;
    }

    public AuthDetailsOutput execute(String token)
        throws InvalidTokenException {
        logger.debug(
            "Get user details request, token length: {}",
            token.length()
        );

        boolean isValid = tokenService.validateAccessToken(token);
        logger.debug("Token validation result: {}", isValid);

        if (!isValid) {
            logger.warn("Invalid access token provided");
            throw new InvalidTokenException();
        }

        // Extract user document from token
        String userDocument = tokenService.extractUserDocument(token);
        logger.debug("Extracted user document from token: {}", userDocument);

        // For ADMIN
        if (adminAuthService.isAdminCredentials(userDocument)) {
            logger.debug("Admin user detected: {}", userDocument);
            User adminUser = adminAuthService.getUser();
            logger.debug(
                "Returning admin details for: {}",
                adminUser.getDocument()
            );
            return new AuthDetailsOutput(
                "Admin",
                adminUser.getDocument(),
                List.of(new GetAreaOutput(0L, "ADMINISTRATION"))
            );
        }

        // For user
        Optional<User> userOpt = userRepository.findByDocument(userDocument);
        logger.debug(
            "Regular user lookup result present: {}",
            userOpt.isPresent()
        );
        if (userOpt.isEmpty()) {
            logger.warn("User not found in repository: {}", userDocument);
            throw new InvalidTokenException("User not found, report"); // TODO: make a correct exception for this case
        }

        User user = userOpt.get();
        logger.debug("Returning user details for: {}", user.getDocument());
        return new AuthDetailsOutput(
            user.getName(),
            user.getDocument(),
            convertAreas(user.getAreas())
        );
    }

    private Collection<GetAreaOutput> convertAreas(Collection<Area> areas) {
        if (areas == null) {
            return null;
        }
        return areas
            .stream()
            .map(area -> new GetAreaOutput(area.getId(), area.getName()))
            .toList();
    }
}
