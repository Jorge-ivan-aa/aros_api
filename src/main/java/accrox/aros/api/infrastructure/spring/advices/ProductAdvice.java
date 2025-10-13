package accrox.aros.api.infrastructure.spring.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;

@RestControllerAdvice
public class ProductAdvice {
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<?> handleProductAlreadyExists(
        ProductAlreadyExistsException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("name", "a product with this name already exists");

        Map<String, Object> response = new HashMap<>();
        response.put("error", "validation failed");
        response.put("message", "the validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
