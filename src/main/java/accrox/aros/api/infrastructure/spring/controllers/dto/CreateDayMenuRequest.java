package accrox.aros.api.infrastructure.spring.controllers.dto;

import java.util.Collection;

import accrox.aros.api.application.dto.daymenu.CreateDayMenuInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateDayMenuRequest (
    @NotNull @NotBlank String name,
    @NotNull @PositiveOrZero Float price,
    @Valid @NotEmpty Collection<CreateDayMenuItemRequest> products
) {
    public CreateDayMenuInput toInput() {
        return new CreateDayMenuInput(
            this.name,
            this.price,
            this.products.stream().map(p -> p.toInput()).toList()
        );
    }
}