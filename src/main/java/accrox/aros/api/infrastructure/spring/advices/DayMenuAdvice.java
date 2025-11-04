package accrox.aros.api.infrastructure.spring.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
//import accrox.aros.api.infrastructure.spring.controllers.DayMenuController;

//@RestControllerAdvice(assignableTypes = DayMenuController.class)
public class DayMenuAdvice {
    @ExceptionHandler(exception = ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("products", "some product doesn't exists");

        Map<String, Object> response = new HashMap<>();
        response.put("error", "validation failed");
        response.put("message", "the validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFound(CategoryNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("categories", "some category doesn't exists");

        Map<String, Object> response = new HashMap<>();
        response.put("error", "validation failed");
        response.put("message", "the validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
