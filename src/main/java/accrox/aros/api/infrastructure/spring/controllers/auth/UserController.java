package accrox.aros.api.infrastructure.spring.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import accrox.aros.api.application.dto.auth.User.CreateUserDTO;
import accrox.aros.api.application.usecases.auth.User.SaveUserUseCase;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private SaveUserUseCase saveUserUseCase;

    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody CreateUserDTO request) {
        this.saveUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}