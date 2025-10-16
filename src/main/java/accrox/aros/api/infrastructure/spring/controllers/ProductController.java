package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.exceptions.product.BadUpdateProductException;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.application.usecases.product.UpdateProductUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateProductRequest;
import accrox.aros.api.infrastructure.spring.dto.UpdateProductAreaRequest;
import accrox.aros.api.infrastructure.spring.dto.UpdateProductRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    @Autowired
    private CreateProductUseCase createProductUseCase;
    @Autowired
    private UpdateAreaProductUseCase updateAreaProductUseCase;
    @Autowired
    private UpdateProductUseCase updateProductUseCase;

    @PutMapping(path = "area")
    public ResponseEntity<?> updateArea(@Valid @RequestBody UpdateProductAreaRequest request) {
        this.updateAreaProductUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateProductRequest request) throws ProductAlreadyExistsException {
        this.createProductUseCase.execute(request.toInput());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<?> update(
            @Valid @RequestBody UpdateProductRequest request) throws BadUpdateProductException {
        this.updateProductUseCase.execute(request.toInput());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
