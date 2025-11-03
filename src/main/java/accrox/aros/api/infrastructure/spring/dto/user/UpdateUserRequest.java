package accrox.aros.api.infrastructure.spring.dto.user;

import java.util.Collection;

import accrox.aros.api.application.dto.user.UpdateUserInput;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
    @NotBlank(message = "required document") String document,
    String name,
    String email,
    String password,
    String address,
    String phone,
    Collection<Long> areas
) {
    public UpdateUserInput toInput() {
        return new UpdateUserInput(
            this.document,
            this.name,
            this.email,
            this.password,
            this.address,
            this.phone,
            this.areas
        );
    }
}
