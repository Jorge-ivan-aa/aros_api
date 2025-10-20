package accrox.aros.api.application.dto.product;

import java.util.Collection;

import accrox.aros.api.application.dto.category.CategoryInput;

public record UpdateProductCategorytInput(
        String name,
        Collection<CategoryInput> categories
) {
}
