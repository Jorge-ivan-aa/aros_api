package accrox.aros.api.application.usecases.auth;

import accrox.aros.api.application.dto.area.GetAreaOuput;
import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.AdminAuthService;
import accrox.aros.api.domain.service.TokenService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GetDetailsUseCase {

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

    public GetUserOuput execute(String token) throws InvalidTokenException {
        System.out.println(
            "DEBUG: GetDetailsUseCase - Token received: " +
                (token != null
                    ? token.substring(0, Math.min(token.length(), 20)) + "..."
                    : "null")
        );

        // Validate the access token
        boolean isValid = tokenService.validateAccessToken(token);
        System.out.println(
            "DEBUG: GetDetailsUseCase - Token validation result: " + isValid
        );

        if (!isValid) {
            System.out.println(
                "DEBUG: GetDetailsUseCase - Token validation failed"
            );
            throw new InvalidTokenException();
        }

        // Extract user document from token
        String userDocument = tokenService.extractUserDocument(token);
        System.out.println(
            "DEBUG: GetDetailsUseCase - Extracted user document: " +
                userDocument
        );

        // Check if this is an admin user
        if (adminAuthService.isAdminCredentials(userDocument)) {
            System.out.println(
                "DEBUG: GetDetailsUseCase - Admin user detected, returning admin details"
            );
            User adminUser = adminAuthService.getUser();
            return new GetUserOuput(
                "Admin",
                adminUser.getDocument(),
                adminUser.getDocument(), // Use document as email for admin
                null, // No phone for admin
                null, // No address for admin
                List.of(new GetAreaOutput(0L, "ADMINISTRATION"))
            );
        }

        // Additional debug: Check if this looks like email or document
        System.out.println(
            "DEBUG: GetDetailsUseCase - Document format check - " +
                "Contains @: " +
                userDocument.contains("@") +
                ", Length: " +
                userDocument.length()
        );

        // Find user by document
        Optional<User> userOpt = userRepository.findByDocument(userDocument);
        if (userOpt.isEmpty()) {
            System.out.println(
                "DEBUG: GetDetailsUseCase - User not found with document: " +
                    userDocument
            );

            // Try to find by email as fallback for debugging
            Optional<User> userByEmailOpt = userRepository.findByEmail(
                userDocument
            );
            System.out.println(
                "DEBUG: GetDetailsUseCase - Fallback search by email found user: " +
                    userByEmailOpt.isPresent()
            );

            throw new InvalidTokenException();
        }

        User user = userOpt.get();
        System.out.println(
            "DEBUG: GetDetailsUseCase - User found: " +
                user.getName() +
                " (" +
                user.getDocument() +
                ")"
        );

        // Convert user to output DTO
        return new GetUserOuput(
            user.getName(),
            user.getDocument(),
            user.getEmail(),
            user.getPhone(),
            user.getAddress(),
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
