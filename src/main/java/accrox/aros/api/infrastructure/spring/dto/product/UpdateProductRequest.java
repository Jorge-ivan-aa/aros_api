package accrox.aros.api.infrastructure.spring.dto.product;


import java.util.Collection;

import accrox.aros.api.application.dto.product.UpdateProductInput;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(
    @NotNull @Positive Long id,
    @Nullable @NotBlank String name,
    @Nullable String description,
    @Nullable Boolean active,
    @NotNull @PositiveOrZero Float price,
    @NotNull @PositiveOrZero Integer preparationTime,
    @NotNull @Positive Long preparationArea,
    @Nullable Collection<Long> categories
) {
    public UpdateProductInput toInput() {
        return new UpdateProductInput(
            this.id,
            this.name,
            this.description,
            this.price,
            this.active,
            this.preparationTime,
            this.preparationArea,
            this.categories
        );
    }
}
