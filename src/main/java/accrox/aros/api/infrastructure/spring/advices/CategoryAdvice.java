package accrox.aros.api.infrastructure.spring.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import accrox.aros.api.application.exceptions.category.CategoryAlreadyExistsException;

@ControllerAdvice
public class CategoryAdvice {
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<?> handleMyCustomException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("name", "A category with this name already exists");

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("message", "The validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
