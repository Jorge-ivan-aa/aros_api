package accrox.aros.api.infrastructure.spring.dto.area;

import accrox.aros.api.application.dto.area.GetAreaInput;
import jakarta.validation.constraints.NotBlank;

public record GetAreaRequest(
        @NotBlank(message = "required name") String name) {

    public GetAreaInput toInput() {
        return new GetAreaInput(this.name);
    }
}
