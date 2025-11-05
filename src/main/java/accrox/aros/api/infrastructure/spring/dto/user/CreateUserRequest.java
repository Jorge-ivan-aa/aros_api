package accrox.aros.api.infrastructure.spring.dto.user;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.user.CreateUserInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record CreateUserRequest(

        @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must not exceed 100 characters") String name,

        @NotBlank(message = "Document is required") String document,

        @NotBlank(message = "Email is required") @Email(message = "Must be a valid email address") String email,

        @NotBlank(message = "Password is required") String password,

        @NotBlank(message = "Address is required") String address,

        String phone,

        @NotNull(message = "Areas are required") Collection<Long> areas

) {
    public CreateUserInput toInput() {
        return new CreateUserInput(
            this.name,
            this.document,
            this.email,
            this.password,
            this.address,
            this.phone,
            this.areas
        );
    }
}
