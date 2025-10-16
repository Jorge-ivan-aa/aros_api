package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.user.GetUserByDocumentInput;
import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.application.usecases.user.*;
import accrox.aros.api.infrastructure.spring.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

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

    @Autowired
    private GetUserByDocumentUseCase getUserByDocumentUseCase;

    @Autowired
    private GetAllUserUseCase getAllUserUseCase;

    @GetMapping
    public ResponseEntity<List<GetUserOuput>> getAllUsers() {
        List<GetUserOuput> users = getAllUserUseCase.execute();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{document}")
    public ResponseEntity<GetUserOuput> getUser(@Valid @PathVariable GetUserByDocumentRequest document) {
        GetUserOuput output = getUserByDocumentUseCase.execute(document.toInput());
        return ResponseEntity.ok(output);
    }


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