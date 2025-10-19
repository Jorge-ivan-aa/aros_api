package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.dto.Table.CreateTableDto;
import accrox.aros.api.application.usecases.Table.CreateTableUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/tables")
public class TableController {

    @Autowired
    private CreateTableUseCase createTableUseCase;

    @Operation(
        summary = "Create a new table",
        description = "This endpoint allows the creation of a new table by providing the required table details in the request body."
    )
    @PostMapping("/create")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateTableDto request
    ) {
        this.createTableUseCase.execute(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}