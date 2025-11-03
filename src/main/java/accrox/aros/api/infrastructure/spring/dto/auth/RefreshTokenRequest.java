package accrox.aros.api.infrastructure.spring.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(
    @NotNull @NotEmpty String refreshToken
) {
}
