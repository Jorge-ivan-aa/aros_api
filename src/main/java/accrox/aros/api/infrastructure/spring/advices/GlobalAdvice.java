package accrox.aros.api.infrastructure.spring.advices;

import accrox.aros.api.application.exceptions.auth.InsecurePasswordException;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import jakarta.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(10)
public class GlobalAdvice {

    private static final Logger logger = LoggerFactory.getLogger(
        GlobalAdvice.class
    );

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
        MethodArgumentNotValidException ex
    ) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex
            .getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String name = ((FieldError) error).getField();
                String message = error.getDefaultMessage();

                errors.put(name, message);
            });

        response.put("error", "Validation failed");
        response.put("message", "The validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
        ValidationException ex
    ) {
        logger.warn("Validation failed: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(
        Exception ex
    ) {
        // Skip authentication exceptions - they are handled by AuthAdvice
        if (
            ex instanceof InvalidCredentialsException ||
            ex instanceof InvalidTokenException ||
            ex instanceof InsecurePasswordException
        ) {
            throw (RuntimeException) ex; // Let AuthAdvice handle it
        }

        logger.error("Unhandled exception", ex);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            body
        );
    }
}
