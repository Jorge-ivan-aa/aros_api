package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.product.UpdateProductAreaInput;
import jakarta.validation.constraints.NotNull;

public record UpdateProductAreaRequest(
        @NotNull(message = "required name") String name,
        GetAreaInput area) {
    public UpdateProductAreaInput toInput() {
        return new UpdateProductAreaInput(
                this.name,
                this.area);
    }
}