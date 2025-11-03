package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.auth.AuthDetailsOutput;
import accrox.aros.api.application.dto.auth.AuthOutput;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.application.usecases.auth.GetDetailsUseCase;
import accrox.aros.api.application.usecases.auth.LoginTokenUseCase;
import accrox.aros.api.application.usecases.auth.RefreshTokenUseCase;
import accrox.aros.api.infrastructure.spring.dto.auth.AuthRequest;
import accrox.aros.api.infrastructure.spring.dto.auth.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/")
public class LoginTokenController {

    private static final Logger logger = LoggerFactory.getLogger(
        LoginTokenController.class
    );

    @Autowired
    private LoginTokenUseCase loginUseCase;

    @Autowired
    private RefreshTokenUseCase refreshUseCase;

    @Autowired
    private GetDetailsUseCase getDetailsUseCase;

    @Operation(
        tags = "Auth Management",
        summary = "Authenticate user and generate token",
        description = "This endpoint authenticates the user with provided credentials and generates a new authentication token."
    )
    @PostMapping(path = "login")
    public ResponseEntity<AuthOutput> login(@RequestBody AuthRequest request)
        throws InvalidCredentialsException {
        logger.debug("Login attempt for user: {}", request.document());

        AuthOutput result = this.loginUseCase.execute(request.toInput());

        logger.debug("Login successful for user: {}", request.document());
        logger.debug(
            "Generated access token length: {}",
            result.access().length()
        );
        logger.debug(
            "Generated refresh token length: {}",
            result.refresh().length()
        );

        return ResponseEntity.ofNullable(result);
    }

    @Operation(
        tags = "Auth Management",
        summary = "Refresh authentication token",
        description = "This endpoint refreshes an existing authentication token. It requires a valid refresh token in the request body."
    )
    @PostMapping(path = "refresh")
    public ResponseEntity<AuthOutput> refresh(
        @RequestBody RefreshTokenRequest request
    ) throws InvalidTokenException {
        logger.debug("Refresh token request received");

        if (
            request == null ||
            request.refreshToken() == null ||
            request.refreshToken().isBlank()
        ) {
            logger.warn("Invalid refresh token request: null or empty token");
            return ResponseEntity.badRequest().build();
        }

        logger.debug(
            "Processing refresh token, length: {}",
            request.refreshToken().length()
        );

        AuthOutput result = this.refreshUseCase.execute(request.refreshToken());

        logger.debug("Token refresh successful");
        logger.debug(
            "Generated new access token length: {}",
            result.access().length()
        );
        logger.debug(
            "Generated new refresh token length: {}",
            result.refresh().length()
        );

        return ResponseEntity.ofNullable(result);
    }

    @Operation(
        tags = "Auth Management",
        summary = "Get current user details",
        description = "This endpoint get all user detail for current session"
    )
    @GetMapping(path = "details")
    public ResponseEntity<AuthDetailsOutput> getDetails(
        @RequestHeader String authorization
    ) throws InvalidTokenException {
        logger.debug("Get user details request received");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Invalid authorization header format");
            return ResponseEntity.badRequest().build();
        }

        String token = authorization.substring(7);
        logger.debug("Extracted access token, length: {}", token.length());

        AuthDetailsOutput result = this.getDetailsUseCase.execute(token);

        logger.debug(
            "User details retrieved successfully for user: {}",
            result.document()
        );

        return ResponseEntity.ofNullable(result);
    }
}
