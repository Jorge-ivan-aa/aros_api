package accrox.aros.api.infrastructure.spring.controllers.dto;

import accrox.aros.api.application.dto.area.DeleteAreaInput;
import jakarta.validation.constraints.NotBlank;

public record DeleteAreaRequest(
        @NotBlank(message = "required name")
        String name
) {

    public DeleteAreaInput toInput() {
        return new DeleteAreaInput(this.name);
    }
}
