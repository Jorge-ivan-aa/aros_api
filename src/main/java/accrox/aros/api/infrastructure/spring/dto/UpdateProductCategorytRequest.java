package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.category.CategoryInput;
import accrox.aros.api.application.dto.product.UpdateProductCategorytInput;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

public record UpdateProductCategorytRequest(
        @NotNull(message = "required document")
        @NotNull(message = "required name")
        String name,
        Collection<CategoryInput> categories

) {
    public UpdateProductCategorytInput toInput() {
        return new UpdateProductCategorytInput(
                this.name,
                this.categories
        );
    }
}
