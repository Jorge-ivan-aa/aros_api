package accrox.aros.api.infrastructure.spring.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.infrastructure.spring.controllers.OrderController;

@RestControllerAdvice(assignableTypes = OrderController.class)
@Order(1)
public class OrderAdvice {
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

    @ExceptionHandler(exception = TableNotFoundException.class)
    public ResponseEntity<?> handleTableNotFound(TableNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("table", "the table's id doesn't exists");

        Map<String, Object> response = new HashMap<>();
        response.put("error", "validation failed");
        response.put("message", "the validation failed for one or more fields");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
