package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.user.GetUserByDocumentInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GetUserByDocumentRequest(
        @NotBlank(message = "required document")
        @NotNull(message = "required document")
        String document
) {
    public GetUserByDocumentInput toInput() {
        return new GetUserByDocumentInput(this.document);
    }
}
