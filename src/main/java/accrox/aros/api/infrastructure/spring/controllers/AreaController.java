package accrox.aros.api.infrastructure.spring.controllers;

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
@RequestMapping(path = "/api/areas")
public class AreaController {

    private static final Logger logger = LoggerFactory.getLogger(
        AreaController.class
    );

    @Autowired
    private SaveAreaUseCase saveAreaUseCase;

    @Autowired
    private DeleteAreaUseCase deleteAreaUseCase;

    @Autowired
    private GetAreaUseCase getAreaRequest;

    @Autowired
    private GetAllAreaUseCase getAllAreaRequest;

    @Operation(
        tags = "Areas Management",
        summary = "Retrieve all areas",
        description = "This endpoint retrieves a list of all available areas."
    )
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAll() {
        logger.info("GET /api/areas/get - Retrieving all areas");
        List<GetAreaOutput> areasOut = getAllAreaRequest.execute();
        logger.info("GET /api/areas/get - Retrieved {} areas", areasOut.size());
        return ResponseEntity.ok(areasOut);
    }

    @Operation(
        tags = "Areas Management",
        summary = "Retrieve a specific area",
        description = "This endpoint retrieves the details of a specific area by its name."
    )
    @GetMapping("/get/{name}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> get(@Valid @PathVariable GetAreaRequest name) {
        logger.info(
            "GET /api/areas/get/{} - Retrieving specific area",
            name.name()
        );
        GetAreaOutput output = this.getAreaRequest.execute(name.toInput());
        logger.info(
            "GET /api/areas/get/{} - Retrieved area: {}",
            name.name(),
            output.name()
        );
        return ResponseEntity.ok(output);
    }

    @Operation(
        tags = "Areas Management",
        summary = "Create a new area",
        description = "This endpoint creates a new area using the provided data."
    )
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody SaveAreaRequest request)
        throws ValidationException {
        logger.info(
            "POST /api/areas/create - Creating new area: {}",
            request.name()
        );
        this.saveAreaUseCase.execute(request.toInput());
        logger.info(
            "POST /api/areas/create - Area '{}' created successfully",
            request.name()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
        tags = "Areas Management",
        summary = "Delete an area by name",
        description = "This endpoint deletes an area identified by its name."
    )
    @DeleteMapping("/delete/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(
        @Valid @PathVariable DeleteAreaRequest name
    ) {
        logger.info("DELETE /api/areas/delete/{} - Deleting area", name.name());
        this.deleteAreaUseCase.execute(name.toInput());
        logger.info(
            "DELETE /api/areas/delete/{} - Area deleted successfully",
            name.name()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
