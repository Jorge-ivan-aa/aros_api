package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.exceptions.product.BadUpdateProductException;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.application.usecases.product.GetAllProductsUseCase;
import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.application.usecases.product.UpdateProductCategoriesUseCase;
import accrox.aros.api.application.usecases.product.UpdateProductUseCase;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.infrastructure.spring.dto.product.CreateProductRequest;
import accrox.aros.api.infrastructure.spring.dto.product.UpdateProductAreaRequest;
import accrox.aros.api.infrastructure.spring.dto.product.UpdateProductCategorytRequest;
import accrox.aros.api.infrastructure.spring.dto.product.UpdateProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(
            ProductController.class);

    @Autowired
    private CreateProductUseCase createProductUseCase;

    @Autowired
    private UpdateAreaProductUseCase updateAreaProductUseCase;

    @Autowired
    private UpdateProductUseCase updateProductUseCase;

    @Autowired
    private UpdateProductCategoriesUseCase updateProductCategoriesUseCase;

    @Autowired
    private GetAllProductsUseCase getAllProductsUseCase;

    @Operation(tags = "Products Management", summary = "Get all products", description = "Retrieves a list of all available products in the system.")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("GET /api/products - Retrieving all products");
        List<Product> products = getAllProductsUseCase.execute();
        logger.info(
                "GET /api/products - Retrieved {} products",
                products.size());
        return ResponseEntity.ok(products);
    }

    @Operation(tags = "Products Management", summary = "Update product area", description = "Updates the area information for a specific product. Requires a valid product identifier and area details.")
    @PutMapping(path = "area")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateArea(
            @Valid @RequestBody UpdateProductAreaRequest request) {
        logger.info(
                "PUT /api/products/area - Updating area for product: {}",
                request.name());
        this.updateAreaProductUseCase.execute(request.toInput());
        logger.info(
                "PUT /api/products/area - Product area updated successfully for product: {}",
                request.name());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(tags = "Products Management", summary = "Create a new product", description = "Creates a new product using the provided details in the request body. Throws an exception if the product already exists.")
    @PostMapping(path = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateProductRequest request) throws ProductAlreadyExistsException {
        logger.info(
                "POST /api/products/create - Creating new product: {}",
                request.name());
        this.createProductUseCase.execute(request.toInput());
        logger.info(
                "POST /api/products/create - Product '{}' created successfully",
                request.name());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(tags = "Products Management", summary = "Update an existing product", description = "Updates an existing product's details. Throws an exception if there are validation issues or the update fails.")
    @PostMapping(path = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(
            @Valid @RequestBody UpdateProductRequest request) throws BadUpdateProductException {
        logger.info(
                "POST /api/products/update - Updating product: {}",
                request.currentName());
        this.updateProductUseCase.execute(request.toInput());
        logger.info(
                "POST /api/products/update - Product: {} updated successfully",
                request.currentName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(tags = "Products Management", summary = "Update product categories", description = "Updates the categories associated with a specific product. Throws an exception if the product does not exist.")
    @PutMapping(path = "/update-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(
            @Valid @RequestBody UpdateProductCategorytRequest request) throws ProductAlreadyExistsException {
        logger.info(
                "PUT /api/products/update-categories - Updating categories for product: {}",
                request.name());
        this.updateProductCategoriesUseCase.execute(request.toInput());
        logger.info(
                "PUT /api/products/update-categories - Categories updated successfully for product: {}",
                request.name());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
