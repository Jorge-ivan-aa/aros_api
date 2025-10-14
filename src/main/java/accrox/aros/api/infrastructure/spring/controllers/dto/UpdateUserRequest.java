package accrox.aros.api.infrastructure.spring.controllers.dto;

import accrox.aros.api.application.dto.user.UpdateUserInput;
import jakarta.validation.constraints.NotBlank;
public record UpdateUserRequest(
        @NotBlank(message = "required document")
        String document,
        String name,
        String email,
        String password,
        String address,
        String phone
) {
    public UpdateUserInput toInput(){
    return new UpdateUserInput(
            this.document,
            this.name,
            this.email,
            this.password,
            this.address,
            this.phone );
    }
}