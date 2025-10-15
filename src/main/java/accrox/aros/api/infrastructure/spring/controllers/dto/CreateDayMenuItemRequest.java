package accrox.aros.api.infrastructure.spring.controllers.dto;

import java.util.Collection;

import accrox.aros.api.application.dto.daymenu.DayMenuCategorySimple;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateDayMenuItemRequest (
    @NotNull @Positive Long category,
    @NotNull @Positive Short position,
    @NotNull @NotEmpty Collection<Long> products
) {
    public DayMenuCategorySimple toInput() {
        return new DayMenuCategorySimple(
            this.category,
            this.position,
            this.products
        );
    }
}
