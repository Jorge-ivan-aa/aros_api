package accrox.aros.api.infrastructure.spring.dto.user;

import java.util.Collection;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.user.UpdateUserAreaInput;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserAreaRequest(
        @NotBlank(message = "required document") String document,
        Collection<GetAreaInput> areas) {

    public UpdateUserAreaInput toInput() {
        return new UpdateUserAreaInput(
                this.document,
                this.areas);
    }
}
