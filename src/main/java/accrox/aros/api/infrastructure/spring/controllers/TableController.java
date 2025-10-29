package accrox.aros.api.infrastructure.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// application DTOs not used directly by the controller (infrastructure requests are used)
import accrox.aros.api.infrastructure.spring.dto.CreateTableRequest;
import accrox.aros.api.infrastructure.spring.dto.CreateTablesRequest;
import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import accrox.aros.api.application.usecases.table.CreateTablesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import accrox.aros.api.application.usecases.table.UpdateTablesCountUseCase;
import accrox.aros.api.infrastructure.spring.dto.UpdateTablesCountRequest;
import jakarta.validation.Valid;

@RestController
public class TableController {

    private static final Logger logger = LoggerFactory.getLogger(
            TableController.class);

    @Autowired
    private CreateTableUseCase createTableUseCase;

    @Autowired
    private CreateTablesUseCase createTablesUseCase;

    @Autowired
    private UpdateTablesCountUseCase updateTablesCountUseCase;

    @Operation(tags = "Tables Management", summary = "Create a new table", description = "This endpoint allows the creation of a new table by providing the required table details in the request body.")
    @PostMapping(path = "/api/tables/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateTableRequest request) {
        logger.info(
                "POST /api/tables/create - Creating new table: {}",
                request.name());
        this.createTableUseCase.execute(request.toInput());
        logger.info(
                "POST /api/tables/create - Table '{}' created successfully",
                request.name());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(tags = "Tables Management", summary = "Create multiple new tables", description = "This endpoint allows the creation of multiple new tables by providing the required table details in the request body.")
    @PostMapping(path = "/api/tables/create-multiple")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMultiple(
            @Valid @RequestBody CreateTablesRequest request) {
        logger.info("POST /api/tables/create-multiple - Creating {} tables", request.count());
        this.createTablesUseCase.execute(request.toInput());
        logger.info("POST /api/tables/create-multiple - Created {} tables", request.count());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(tags = "Tables Management", summary = "Update total tables count", description = "Adjust the total number of tables to the provided total: create or delete tables to match the target count.")
    @PostMapping(path = "/api/tables/update-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCount(
            @Valid @RequestBody UpdateTablesCountRequest request) {
        logger.info("POST /api/tables/update-count - Updating total tables to {}", request.total());
        this.updateTablesCountUseCase.execute(request.toInput());
        logger.info("POST /api/tables/update-count - Updated total tables to {}", request.total());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
