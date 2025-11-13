package accrox.aros.api.infrastructure.spring.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;

@RestControllerAdvice
@Order(1)
public class ProductAdvice {
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<?> handleProductAlreadyExists(
            ProductAlreadyExistsException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "a product with that name already exists.");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
