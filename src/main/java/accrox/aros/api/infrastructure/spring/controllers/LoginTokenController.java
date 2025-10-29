package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.auth.AuthOutput;
import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.application.usecases.auth.GetDetailsUseCase;
import accrox.aros.api.application.usecases.auth.LoginTokenUseCase;
import accrox.aros.api.application.usecases.auth.RefreshTokenUseCase;
import accrox.aros.api.infrastructure.spring.dto.auth.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
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
        return ResponseEntity.ofNullable(
            this.loginUseCase.execute(request.toInput())
        );
    }

    @Operation(
        tags = "Auth Management",
        summary = "Refresh authentication token",
        description = "This endpoint refreshes an existing authentication token. It requires a valid Bearer token in the Authorization header."
    )
    @PostMapping(path = "refresh")
    public ResponseEntity<AuthOutput> refresh(
        @RequestHeader String authorization
    ) throws InvalidTokenException {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String token = authorization.substring(7);

        return ResponseEntity.ofNullable(this.refreshUseCase.execute(token));
    }

    @Operation(
        tags = "Auth Management",
        summary = "Get current user details",
        description = "This endpoint get all user detail for current session"
    )
    @GetMapping(path = "details")
    public ResponseEntity<GetUserOuput> getDetails(
        @RequestHeader String authorization
    ) throws InvalidTokenException {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authorization.substring(7);
        return ResponseEntity.ofNullable(this.getDetailsUseCase.execute(token));
    }
}
