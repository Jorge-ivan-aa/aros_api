package accrox.aros.api.infrastructure.spring.dto.area;

import accrox.aros.api.application.dto.area.SaveAreaInput;
import jakarta.validation.constraints.NotBlank;

public record SaveAreaRequest(
        @NotBlank(message = "required name") String name) {
    public SaveAreaInput toInput() {
        return new SaveAreaInput(this.name);
    }
}
