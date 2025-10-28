package accrox.aros.api.infrastructure.spring.advices;

import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
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
}
