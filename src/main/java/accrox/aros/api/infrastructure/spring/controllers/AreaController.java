package accrox.aros.api.infrastructure.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.exceptions.ValidationException;
import accrox.aros.api.application.usecases.area.DeleteAreaUseCase;
import accrox.aros.api.application.usecases.area.GetAllAreaUseCase;
import accrox.aros.api.application.usecases.area.GetAreaUseCase;
import accrox.aros.api.application.usecases.area.SaveAreaUseCase;
import accrox.aros.api.infrastructure.spring.dto.DeleteAreaRequest;
import accrox.aros.api.infrastructure.spring.dto.GetAreaRequest;
import accrox.aros.api.infrastructure.spring.dto.SaveAreaRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/areas")
public class AreaController {

    @Autowired
    private SaveAreaUseCase saveAreaUseCase;

    @Autowired
    private DeleteAreaUseCase deleteAreaUseCase;

    @Autowired
    private GetAreaUseCase getAreaRequest;

    @Autowired
    private GetAllAreaUseCase getAllAreaRequest;

    @Operation(summary = "Retrieve all areas", description = "This endpoint retrieves a list of all available areas.")
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        List<GetAreaOutput> areasOut = getAllAreaRequest.execute();
        return ResponseEntity.ok(areasOut);
    }

    @Operation(summary = "Retrieve a specific area", description = "This endpoint retrieves the details of a specific area by its name.")
    @GetMapping("/get/{name}")
    public ResponseEntity<?> get(@Valid @PathVariable GetAreaRequest name) {
        GetAreaOutput output = this.getAreaRequest.execute(name.toInput());
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Create a new area", description = "This endpoint creates a new area using the provided data.")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SaveAreaRequest request) throws ValidationException {
        this.saveAreaUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete an area by name", description = "This endpoint deletes an area identified by its name.")
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@Valid @PathVariable DeleteAreaRequest name) {
        this.deleteAreaUseCase.execute(name.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}