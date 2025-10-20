package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.usecases.product.UpdateProductCategoriesUseCase;
import accrox.aros.api.infrastructure.spring.dto.UpdateProductCategorytRequest;
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
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    private UpdateProductCategoriesUseCase updateProductCategoriesUseCase;

    @Operation(
        summary = "Update product area",
        description = "Updates the area information for a specific product. Requires a valid product identifier and area details."
    )
    @PutMapping(path = "area")
    public ResponseEntity<?> updateArea(@Valid @RequestBody UpdateProductAreaRequest request) {
        this.updateAreaProductUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "Create a new product",
        description = "Creates a new product using the provided details in the request body. Throws an exception if the product already exists."
    )
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateProductRequest request) throws ProductAlreadyExistsException {
        this.createProductUseCase.execute(request.toInput());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing product",
        description = "Updates an existing product's details. Throws an exception if there are validation issues or the update fails."
    )
    @PostMapping(path = "/update")
    public ResponseEntity<?> update(
            @Valid @RequestBody UpdateProductRequest request) throws BadUpdateProductException {
        this.updateProductUseCase.execute(request.toInput());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(
        summary = "Update product categories",
        description = "Updates the categories associated with a specific product. Throws an exception if the product does not exist."
    )
    @PutMapping(path = "/update-categories")
    public ResponseEntity<?> updateCategory(
        @Valid @RequestBody UpdateProductCategorytRequest request
    ) throws ProductAlreadyExistsException {
        this.updateProductCategoriesUseCase.execute(request.toInput());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}