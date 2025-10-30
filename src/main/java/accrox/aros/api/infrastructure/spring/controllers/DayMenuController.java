package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.usecases.daymenu.GetAllDayMenusUseCase;
import accrox.aros.api.application.usecases.product.CreateDayMenuUseCase;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.infrastructure.spring.dto.daymenu.CreateDayMenuRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/daymenu")
public class DayMenuController {

    private static final Logger logger = LoggerFactory.getLogger(
            DayMenuController.class);

    @Autowired
    private CreateDayMenuUseCase createDayMenuUseCase;

    @Autowired
    private GetAllDayMenusUseCase getAllDayMenusUseCase;

    @Operation(tags = "Day Menu Management", summary = "Get all day menus", description = "Retrieves a list of all available day menus in the system.")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Daymenu>> getAllDayMenus() {
        logger.info("GET /api/daymenu - Retrieving all day menus");
        List<Daymenu> dayMenus = getAllDayMenusUseCase.execute();
        logger.info(
                "GET /api/daymenu - Retrieved {} day menus",
                dayMenus.size());
        return ResponseEntity.ok(dayMenus);
    }

    @Operation(tags = "Day Menu Management", summary = "Create a new day menu", description = "This endpoint allows the creation of a new day menu. It requires valid product and category IDs.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateDayMenuRequest request)
            throws ProductAlreadyExistsException, ProductNotFoundException, CategoryNotFoundException {
        logger.info(
                "POST /api/daymenu - Creating new day menu with {} products",
                request.products().size());
        this.createDayMenuUseCase.execute(request.toInput());
        logger.info("POST /api/daymenu - Day menu created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
