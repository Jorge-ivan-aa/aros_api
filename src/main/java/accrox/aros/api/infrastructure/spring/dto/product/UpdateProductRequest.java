package accrox.aros.api.infrastructure.spring.dto.product;

import org.hibernate.validator.constraints.UniqueElements;

import accrox.aros.api.application.dto.product.UpdateProductInput;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(
        @NotNull @NotBlank @UniqueElements(message = "Name product must be unique") String currentName,
        @Nullable String newName,
        @Nullable String description,
        @NotNull @PositiveOrZero Float price,
        @NotNull @PositiveOrZero Integer preparationTime) {
    public UpdateProductInput toInput() {
        return new UpdateProductInput(
                this.currentName,
                this.newName,
                this.description,
                this.price,
                this.preparationTime);
    }
}
