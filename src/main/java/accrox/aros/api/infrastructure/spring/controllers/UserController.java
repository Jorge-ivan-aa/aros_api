package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.usecases.user.DeleteUserUseCase;
import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserAreaUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateUserRequest;
import accrox.aros.api.infrastructure.spring.dto.DeleteUserRequest;
import accrox.aros.api.infrastructure.spring.dto.UpdateUserAreaRequest;
import accrox.aros.api.infrastructure.spring.dto.UpdateUserRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private SaveUserUseCase saveUserUseCase;

    @Autowired
    private DeleteUserUseCase deletUserUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private UpdateUserAreaUseCase updateUserAreaUseCase;

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody CreateUserRequest request) {
        this.saveUserUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete-user/{document}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable DeleteUserRequest document){
        this.deletUserUseCase.execute(document.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update-area")
    public ResponseEntity<Void> updateArea(
           @Valid @RequestBody UpdateUserAreaRequest request
    ){
        this.updateUserAreaUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update-user")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest  ){
        this.updateUserUseCase.execute(updateUserRequest.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}