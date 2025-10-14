package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.dto.auth.AuthRequestDto;
import accrox.aros.api.application.dto.auth.AuthTokenReponseDto;
import accrox.aros.api.application.exceptions.auth.InvalidCredentialsException;
import accrox.aros.api.application.exceptions.auth.InvalidTokenException;
import accrox.aros.api.application.usecases.auth.LoginTokenUseCase;
import accrox.aros.api.application.usecases.auth.RefreshTokenUseCase;

@RestController()
@RequestMapping(path = "/api/")
public class LoginTokenController {
    @Autowired
    private LoginTokenUseCase loginUseCase;

    @Autowired
    private RefreshTokenUseCase refreshUseCase;

    @PostMapping(path = "login")
    public ResponseEntity<AuthTokenReponseDto> login(
            @RequestBody AuthRequestDto request) throws InvalidCredentialsException {
        return ResponseEntity.ofNullable(this.loginUseCase.execute(request));
    }

    @PostMapping(path = "refresh")
    public ResponseEntity<AuthTokenReponseDto> refresh(
            @RequestHeader String authorization) throws InvalidTokenException {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String token = authorization.substring(7);

        return ResponseEntity.ofNullable(this.refreshUseCase.execute(token));
    }

    @RequestMapping(path = "proof")
    public Object ah(Authentication auth) {
        return auth;
    }
}
