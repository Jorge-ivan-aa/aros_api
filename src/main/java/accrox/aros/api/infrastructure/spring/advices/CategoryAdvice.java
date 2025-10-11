package accrox.aros.api.infrastructure.spring.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import accrox.aros.api.application.exceptions.ValidationException;

@ControllerAdvice
public class CategoryAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleMyCustomException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CREATED);
    }
}
