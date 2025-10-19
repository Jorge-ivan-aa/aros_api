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
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
        summary = "Retrieve all users",
        description = "Fetches a list of all registered users."
    )
    @GetMapping
    public ResponseEntity<List<GetUserOuput>> getAllUsers() {
        List<GetUserOuput> users = getAllUserUseCase.execute();
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Retrieve specific user by document",
        description = "Fetches a user's information based on the provided document identifier."
    )
    @GetMapping("/get/{document}")
    public ResponseEntity<GetUserOuput> getUser(@Valid @PathVariable GetUserByDocumentRequest document) {
        GetUserOuput output = getUserByDocumentUseCase.execute(document.toInput());
        return ResponseEntity.ok(output);
    }

    @Operation(
        summary = "Save a new user",
        description = "Creates a new user using the information provided in the request body."
    )
    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody CreateUserRequest request) {
        this.saveUserUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
        summary = "Delete a user by document",
        description = "Deletes a user based on the provided document identifier."
    )
    @DeleteMapping("/delete-user/{document}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable DeleteUserRequest document){
        this.deletUserUseCase.execute(document.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "Update user area",
        description = "Updates the area information for a given user based on the provided details."
    )
    @PutMapping("/update-area")
    public ResponseEntity<Void> updateArea(
            @Valid @RequestBody UpdateUserAreaRequest request
    ){
        this.updateUserAreaUseCase.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "Update user details",
        description = "Updates a user's details using the information provided in the request body."
    )
    @PutMapping("/update-user")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest  ){
        this.updateUserUseCase.execute(updateUserRequest.toInput());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}