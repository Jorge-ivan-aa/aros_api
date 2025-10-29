package accrox.aros.api.infrastructure.spring.dto.product;

import accrox.aros.api.application.dto.product.CreateProductInput;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProductRequest(
        @NotNull @NotBlank String name,
        @Nullable String description,
        @NotNull @PositiveOrZero Float price,
        @NotNull @PositiveOrZero Integer preparationTime) {
    public CreateProductInput toInput() {
        return new CreateProductInput(
                this.name,
                this.description,
                this.price,
                this.preparationTime);
    }
}
