package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.Table.CreateTableDto;
import accrox.aros.api.application.usecases.Table.CreateTableUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tables")
public class TableController {

    private static final Logger logger = LoggerFactory.getLogger(
        TableController.class
    );

    @Autowired
    private CreateTableUseCase createTableUseCase;

    @Operation(
        tags = "Tables Management",
        summary = "Create a new table",
        description = "This endpoint allows the creation of a new table by providing the required table details in the request body."
    )
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateTableDto request
    ) {
        logger.info(
            "POST /api/tables/create - Creating new table: {}",
            request.getName()
        );
        this.createTableUseCase.execute(request);
        logger.info(
            "POST /api/tables/create - Table '{}' created successfully",
            request.getName()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
