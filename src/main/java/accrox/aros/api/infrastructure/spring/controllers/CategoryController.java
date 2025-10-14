package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.exceptions.category.CategoryAlreadyExistsException;
import accrox.aros.api.application.usecases.category.CreateCategoryUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateCategoryRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/")
public class CategoryController {
    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @PostMapping(path = "category")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateCategoryRequest request) throws CategoryAlreadyExistsException {
        this.createCategoryUseCase.execute(request.toInput());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
