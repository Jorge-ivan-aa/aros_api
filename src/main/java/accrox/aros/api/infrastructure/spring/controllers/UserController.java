package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.user.GetUserOuput;
import accrox.aros.api.application.usecases.user.DeleteUserUseCase;
import accrox.aros.api.application.usecases.user.GetAllUserUseCase;
import accrox.aros.api.application.usecases.user.GetUserByDocumentUseCase;
import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserAreaUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserUseCase;
import accrox.aros.api.infrastructure.spring.dto.CreateUserRequest;
import accrox.aros.api.infrastructure.spring.dto.DeleteUserRequest;
import accrox.aros.api.infrastructure.spring.dto.GetUserByDocumentRequest;
import accrox.aros.api.infrastructure.spring.dto.UpdateUserAreaRequest;
import accrox.aros.api.infrastructure.spring.dto.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(
        UserController.class
    );

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
        tags = "Users Management",
        summary = "Retrieve all users",
        description = "Fetches a list of all registered users. Requires ADMIN role."
    )
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GetUserOuput>> getAllUsers() {
        logger.info("GET /api/users - Retrieving all users");
        List<GetUserOuput> users = getAllUserUseCase.execute();
        logger.info("GET /api/users - Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @Operation(
        tags = "Users Management",
        summary = "Retrieve specific user by document",
        description = "Fetches a user's information based on the provided document identifier. Requires ADMIN role."
    )
    @GetMapping("/get/{document}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetUserOuput> getUser(
            @PathVariable String  document
    ) {
        logger.info(
            "GET /api/users/get/{} - Retrieving user by document",
            document
        );
        GetUserOuput output = getUserByDocumentUseCase.execute(
            document
        );
        logger.info(
            "GET /api/users/get/{} - Retrieved user: {}",
            document,
            output.name()
        );
        return ResponseEntity.ok(output);
    }

    @Operation(
        tags = "Users Management",
        summary = "Save a new user",
        description = "Creates a new user using the information provided in the request body. Requires ADMIN role."
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> saveUser(
        @Valid @RequestBody CreateUserRequest request
    ) {
        logger.info(
            "POST /api/users/save - Creating new user: {}",
            request.document()
        );
        this.saveUserUseCase.execute(request.toInput());
        logger.info(
            "POST /api/users/save - User '{}' created successfully",
            request.document()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
        tags = "Users Management",
        summary = "Delete a user by document",
        description = "Deletes a user based on the provided document identifier. Requires ADMIN role."
    )
    @DeleteMapping("/delete-user/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
        @PathVariable String document
    ) {
        logger.info(
            "DELETE /api/users/delete-user/{} - Deleting user by document",
            document
        );
        this.deletUserUseCase.execute(document);
        logger.info(
            "DELETE /api/users/delete-user/{} - User deleted successfully",
            document        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        tags = "Users Management",
        summary = "Update user area",
        description = "Updates the area information for a given user based on the provided details. Requires ADMIN role."
    )
    @PutMapping("/update-area")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateArea(
        @Valid @RequestBody UpdateUserAreaRequest request
    ) {
        logger.info(
            "PUT /api/users/update-area - Updating areas for user ID: {}",
            request.document()
        );
        this.updateUserAreaUseCase.execute(request.toInput());
        logger.info(
            "PUT /api/users/update-area - Areas updated successfully for user ID: {}",
            request.document()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        tags = "Users Management",
        summary = "Update user details",
        description = "Updates a user's details using the information provided in the request body. Requires ADMIN role."
    )
    @PutMapping("/update-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUser(
        @Valid @RequestBody UpdateUserRequest updateUserRequest
    ) {
        logger.info(
            "PUT /api/users/update-user - Updating user ID: {}",
            updateUserRequest.document()
        );
        this.updateUserUseCase.execute(updateUserRequest.toInput());
        logger.info(
            "PUT /api/users/update-user - User ID: {} updated successfully",
            updateUserRequest.document()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
