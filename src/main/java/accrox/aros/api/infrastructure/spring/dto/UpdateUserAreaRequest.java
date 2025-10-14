package accrox.aros.api.infrastructure.spring.controllers.dto;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.user.UpdateUserAreaInput;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;

public record UpdateUserAreaRequest(
        @NotBlank(message = "required name")
        String document,
         Collection<GetAreaInput> areas
) {

    public UpdateUserAreaInput toInput() {
        return new UpdateUserAreaInput(
                this.document,
                this.areas
        );
    }
}
