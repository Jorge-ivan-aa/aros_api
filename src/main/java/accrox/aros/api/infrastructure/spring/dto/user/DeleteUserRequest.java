package accrox.aros.api.infrastructure.spring.dto.user;

import accrox.aros.api.application.dto.user.DeleteUserInput;
import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(
        @NotBlank(message = "Document is required") String document) {
    public DeleteUserInput toInput() {
        return new DeleteUserInput(
                this.document);
    }
}
