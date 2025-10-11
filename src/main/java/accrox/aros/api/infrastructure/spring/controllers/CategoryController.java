package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.dto.category.CreateCategoryDto;
import accrox.aros.api.application.exceptions.ValidationException;
import accrox.aros.api.application.usecases.category.CreateCategoryUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @GetMapping(path = "/")
    public ResponseEntity<?> create(
        @RequestBody CreateCategoryDto request
    ) throws ValidationException {
        this.createCategoryUseCase.execute(request);

        // return ResponseEntity.ofNullable(request);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
