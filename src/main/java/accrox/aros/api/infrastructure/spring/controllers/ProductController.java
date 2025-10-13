package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.infrastructure.spring.controllers.dto.CreateProductRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    @Autowired
    private CreateProductUseCase createProductUseCase;

    @PostMapping(path = "")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateProductRequest request
    ) throws ProductAlreadyExistsException {
        this.createProductUseCase.execute(request.toInput());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
