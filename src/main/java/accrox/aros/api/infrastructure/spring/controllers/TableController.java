package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.table.TableStatusOutput;
import accrox.aros.api.application.dto.table.TablesAmountOutput;
import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import accrox.aros.api.application.usecases.table.CreateTablesUseCase;
import accrox.aros.api.application.usecases.table.GetTablesAmountUseCase;
import accrox.aros.api.application.usecases.table.GetTablesStatusUseCase;
import accrox.aros.api.application.usecases.table.UpdateTablesCountUseCase;
import accrox.aros.api.infrastructure.spring.dto.table.CreateTableRequest;
import accrox.aros.api.infrastructure.spring.dto.table.CreateTablesRequest;
import accrox.aros.api.infrastructure.spring.dto.table.UpdateTablesCountRequest;
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
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private GetTablesStatusUseCase getTablesStatusUseCase;

    @Autowired
    private GetTablesAmountUseCase getTablesAmountUseCase;

    @Operation(tags = "Tables Management", summary = "Get tables status", description = "This endpoint returns the current status of all tables (AVAILABLE or OCCUPIED) based on active orders from today.")
    @GetMapping(path = "/api/tables/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('WAITER')")
    public ResponseEntity<List<TableStatusOutput>> getStatus() {
        logger.info("GET /api/tables/status - Getting tables status");
        List<TableStatusOutput> tablesStatus = this.getTablesStatusUseCase.execute();
        logger.info(
                "GET /api/tables/status - Retrieved status for {} tables",
                tablesStatus.size());
        return new ResponseEntity<>(tablesStatus, HttpStatus.OK);
    }

    @Operation(tags = "Tables Management", summary = "Get total tables amount", description = "This endpoint returns the total number of tables currently stored in the database.")
    @GetMapping(path = "/api/tables/get-amount")
    @PreAuthorize("hasRole('ADMIN') or hasRole('WAITER')")
    public ResponseEntity<TablesAmountOutput> getAmount() {
        logger.info("GET /api/tables/get-amount - Getting total tables amount");
        TablesAmountOutput amount = this.getTablesAmountUseCase.execute();
        logger.info(
                "GET /api/tables/get-amount - Total tables: {}",
                amount.amount());
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }

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
        logger.info(
                "POST /api/tables/create-multiple - Creating {} tables",
                request.count());
        this.createTablesUseCase.execute(request.toInput());
        logger.info(
                "POST /api/tables/create-multiple - Created {} tables",
                request.count());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(tags = "Tables Management", summary = "Update total tables count", description = "Adjust the total number of tables to the provided total: create or delete tables to match the target count.")
    @PostMapping(path = "/api/tables/update-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCount(
            @Valid @RequestBody UpdateTablesCountRequest request) {
        logger.info(
                "POST /api/tables/update-count - Updating total tables to {}",
                request.total());
        this.updateTablesCountUseCase.execute(request.toInput());
        logger.info(
                "POST /api/tables/update-count - Updated total tables to {}",
                request.total());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
