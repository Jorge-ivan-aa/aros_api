package accrox.aros.api.infrastructure.spring.dto.user;

import org.hibernate.validator.constraints.UniqueElements;

import accrox.aros.api.application.dto.user.UpdateUserInput;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "required document") @UniqueElements(message = "document must be unique") String document,
        String name,
        @UniqueElements(message = "document must be unique") String email,
        String password,
        String address,
        String phone) {
    public UpdateUserInput toInput() {
        return new UpdateUserInput(
                this.document,
                this.name,
                this.email,
                this.password,
                this.address,
                this.phone);
    }
}
