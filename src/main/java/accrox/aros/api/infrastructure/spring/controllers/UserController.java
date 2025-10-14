package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.usecases.user.DeleteUserUseCase;
import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateUserRequest;
import accrox.aros.api.infrastructure.spring.dto.DeleteUserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private SaveUserUseCase saveUserUseCase;

    @Autowired
    private DeleteUserUseCase deletUserUseCase;

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody CreateUserRequest request) {
        this.saveUserUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete-user/{document}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable DeleteUserRequest document) {
        this.deletUserUseCase.execute(document.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}