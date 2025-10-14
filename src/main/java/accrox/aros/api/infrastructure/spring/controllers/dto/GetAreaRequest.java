package accrox.aros.api.infrastructure.spring.controllers.dto;

import accrox.aros.api.application.dto.area.DeleteAreaInput;
import accrox.aros.api.application.dto.area.GetAreaInput;
import jakarta.validation.constraints.NotBlank;

public record GetAreaRequest(
        @NotBlank(message = "required name")
        String name
) {

    public GetAreaInput toInput() {
        return new GetAreaInput(this.name);
    }
}
