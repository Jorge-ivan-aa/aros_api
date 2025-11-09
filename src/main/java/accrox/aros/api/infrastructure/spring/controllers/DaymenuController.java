package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.daymenu.DaymenuOutput;
import accrox.aros.api.application.usecases.daymenu.CreateDayMenuUseCase;
import accrox.aros.api.application.usecases.daymenu.GetCurrentDaymenuUseCase;
import accrox.aros.api.application.usecases.daymenu.GetDaymenuByDateUseCase;
import accrox.aros.api.infrastructure.spring.dto.daymenu.CreateDayMenuRequest;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/daymenu")
public class DaymenuController {

    private static final Logger logger = LoggerFactory.getLogger(
            DaymenuController.class);

    @Autowired
    private CreateDayMenuUseCase createDayMenuUseCase;

    @Autowired
    private GetCurrentDaymenuUseCase getCurrentDaymenuUseCase;

    @Autowired
    private GetDaymenuByDateUseCase getDaymenuByDateUseCase;

    @Operation(tags = "Day Menu Management", summary = "Get day menu by date", description = "This endpoint returns the day menu for a specific date with its categories and products.")
    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('WAITER')")
    public ResponseEntity<?> getDaymenuByDate(@PathVariable String date) {
        logger.info(
                "GET /api/daymenu/date/{} - Getting day menu for date",
                date);

        try {
            LocalDate targetDate = LocalDate.parse(date);
            Optional<DaymenuOutput> daymenu = this.getDaymenuByDateUseCase.execute(targetDate);

            if (daymenu.isPresent()) {
                logger.info("GET /api/daymenu/date/{} - Day menu found", date);
                return new ResponseEntity<>(daymenu.get(), HttpStatus.OK);
            } else {
                logger.warn(
                        "GET /api/daymenu/date/{} - No day menu found for date",
                        date);
                return new ResponseEntity<>(
                        "No day menu found for date: " + date,
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(
                    "GET /api/daymenu/date/{} - Invalid date format: {}",
                    date,
                    e.getMessage());
            return new ResponseEntity<>(
                    "Invalid date format. Use YYYY-MM-DD",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(tags = "Day Menu Management", summary = "Create a new day menu", description = "This endpoint creates a new day menu with organized categories and products. The creation date is automatically set to the current day.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDayMenu(@Valid @RequestBody CreateDayMenuRequest request) {
        logger.info("POST /api/daymenu - Creating day menu: {}", request.name());

        try {
            this.createDayMenuUseCase.execute(request.toInput());
            logger.info("POST /api/daymenu - Day menu created successfully: {}", request.name());
            return new ResponseEntity<>("Day menu created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("POST /api/daymenu - Error creating day menu: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(tags = "Day Menu Management", summary = "Get current day menu", description = "This endpoint returns the menu of the current day with all its categories and products.")
    @GetMapping("/current")
    @PreAuthorize("hasRole('ADMIN') or hasRole('WAITER')")
    public ResponseEntity<?> getCurrentDaymenu() {
        logger.info("GET /api/daymenu/current - Getting current day menu");

        Optional<DaymenuOutput> currentDaymenu = this.getCurrentDaymenuUseCase.execute();

        if (currentDaymenu.isPresent()) {
            logger.info("GET /api/daymenu/current - Current day menu found");
            return new ResponseEntity<>(currentDaymenu.get(), HttpStatus.OK);
        } else {
            logger.warn(
                    "GET /api/daymenu/current - No day menu found for today");
            return new ResponseEntity<>(
                    "No day menu found for today",
                    HttpStatus.NOT_FOUND);
        }
    }
}
