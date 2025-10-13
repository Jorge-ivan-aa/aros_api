package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.dto.Table.CreateTableDto;
import accrox.aros.api.application.usecases.Table.CreateTableUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class TableController {
    
    @Autowired
    private CreateTableUseCase createTableUseCase;

    public ResponseEntity<?> create(
        @RequestBody CreateTableDto request
    ) {
        this.createTableUseCase.execute(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
