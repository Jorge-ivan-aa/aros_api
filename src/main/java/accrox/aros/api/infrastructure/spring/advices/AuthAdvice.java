package accrox.aros.api.infrastructure.spring.advices;

import accrox.aros.api.application.exceptions.auth.InsecurePasswordException;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.application.exceptions.auth.RefreshTokenNotFoundException;
import accrox.aros.api.application.exceptions.auth.TokenExpiredException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(5) // Higher priority than GlobalAdvice
public class AuthAdvice {

    private static final Logger logger = LoggerFactory.getLogger(
        AuthAdvice.class
    );

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<
        Map<String, String>
    > handleInvalidCredentialsException(InvalidCredentialsException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Authentication failed");
        response.put("message", "Invalid email or password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(
        BadCredentialsException ex
    ) {
        logger.warn("Bad credentials: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Authentication failed");
        response.put("message", "Invalid credentials");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InsecurePasswordException.class)
    public ResponseEntity<Map<String, String>> handleInsecurePasswordException(
        InsecurePasswordException ex
    ) {
        logger.warn("Insecure password detected: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Insecure password");
        response.put(
            "message",
            "The provided password does not meet security requirements"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTokenException(
        InvalidTokenException ex
    ) {
        logger.warn("Invalid token: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid token");
        response.put("message", "The provided token is invalid or expired");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleTokenExpiredException(
        TokenExpiredException ex
    ) {
        logger.warn("Token expired: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Token expired");
        response.put("message", "The provided token has expired");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<
        Map<String, String>
    > handleRefreshTokenNotFoundException(RefreshTokenNotFoundException ex) {
        logger.warn("Refresh token not found: {}", ex.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("error", "Refresh token not found");
        response.put("message", "The provided refresh token does not exist");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
