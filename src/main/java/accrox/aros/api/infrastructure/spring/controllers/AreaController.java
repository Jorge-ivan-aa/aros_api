package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.exceptions.ValidationException;
import accrox.aros.api.application.usecases.area.DeleteAreaUseCase;
import accrox.aros.api.application.usecases.area.GetAllAreaUseCase;
import accrox.aros.api.application.usecases.area.SaveAreaUseCase;
import accrox.aros.api.application.usecases.area.GetAreaUseCase;
import accrox.aros.api.infrastructure.spring.controllers.dto.DeleteAreaRequest;
import accrox.aros.api.infrastructure.spring.controllers.dto.GetAreaRequest;
import accrox.aros.api.infrastructure.spring.controllers.dto.SaveAreaRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        List<GetAreaOutput> areasOut = getAllAreaRequest.execute();
        return ResponseEntity.ok(areasOut);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<?> get(@Valid @PathVariable GetAreaRequest name) {
        GetAreaOutput output = this.getAreaRequest.execute(name.toInput());
        return ResponseEntity.ok(output);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SaveAreaRequest request) throws ValidationException {
        this.saveAreaUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@Valid @PathVariable DeleteAreaRequest name)  {
        this.deleteAreaUseCase.execute(name.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
