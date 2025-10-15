package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.infrastructure.spring.dto.UpdateProductAreaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateProductRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    @Autowired
    private CreateProductUseCase createProductUseCase;
    @Autowired
    private UpdateAreaProductUseCase updateAreaProductUseCase;

    @PutMapping(path = "area")
    public ResponseEntity<?> updateArea(@Valid @RequestBody UpdateProductAreaRequest request){
        this.updateAreaProductUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateProductRequest request) throws ProductAlreadyExistsException {
        this.createProductUseCase.execute(request.toInput());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
