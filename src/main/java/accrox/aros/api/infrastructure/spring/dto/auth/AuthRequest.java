package accrox.aros.api.infrastructure.spring.dto.auth;

import accrox.aros.api.application.dto.auth.AuthInput;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
    @NotNull @NotEmpty String document,
    @NotNull @NotEmpty String password
) {
    public AuthInput toInput() {
        return new AuthInput(document, password);
    }
}
