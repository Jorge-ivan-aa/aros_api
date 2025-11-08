package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.exceptions.category.CategoryAlreadyExistsException;
import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.application.usecases.category.CreateCategoryUseCase;
import accrox.aros.api.application.usecases.category.DeleteCategoryUseCase;
import accrox.aros.api.application.usecases.category.GetAllCategoriesUseCase;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.infrastructure.spring.dto.category.CreateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(
        CategoryController.class
    );

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private GetAllCategoriesUseCase getAllCategoriesUseCase;

    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Operation(
        tags = "Categories Management",
        summary = "Get all categories",
        description = "Retrieves a list of all available categories in the system."
    )
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Category>> getAllCategories() {
        logger.info("GET /api/categories - Retrieving all categories");
        List<Category> categories = getAllCategoriesUseCase.execute();
        logger.info(
            "GET /api/categories - Retrieved {} categories",
            categories.size()
        );
        return ResponseEntity.ok(categories);
    }

    @Operation(
        tags = "Categories Management",
        summary = "Create a new category",
        description = "This endpoint allows the creation of a new category. If the category already exists, a CategoryAlreadyExistsException will be thrown."
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateCategoryRequest request
    ) throws CategoryAlreadyExistsException {
        logger.info(
            "POST /api/categories - Creating new category: {}",
            request.getName()
        );
        this.createCategoryUseCase.execute(request.toInput());
        logger.info(
            "POST /api/categories - Category '{}' created successfully",
            request.getName()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
        tags = "Categories Management",
        summary = "Delete categories",
        description = "This endpoint allows delete Categories"
    )
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
        throws CategoryNotFoundException {
        this.deleteCategoryUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}
