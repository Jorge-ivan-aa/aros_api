package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.usecases.product.CreateDayMenuUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateDayMenuRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/daymenu")
public class DayMenuController {
    @Autowired
    private CreateDayMenuUseCase createDayMenuUseCase;

    @PostMapping
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateDayMenuRequest request
    ) throws
        ProductAlreadyExistsException,
        ProductNotFoundException,
        CategoryNotFoundException
    {
        this.createDayMenuUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
