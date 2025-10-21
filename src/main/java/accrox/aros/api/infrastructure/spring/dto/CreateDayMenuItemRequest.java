package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.daymenu.DayMenuCategorySimple;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collection;

@Schema(
    description = "Represents a category section within a day menu with its products",
    example = """
    {
      "category": 1,
      "position": 1,
      "products": [101, 102]
    }
    """
)
public record CreateDayMenuItemRequest(
    @Schema(
        description = "ID of the category (e.g., 1=Sopas, 2=Principios, 3=Carnes, 4=Bebidas, 5=Postres)",
        example = "1"
    )
    @NotNull
    @Positive
    Long category,

    @Schema(
        description = "Display position of this category in the menu (1=first, 2=second, etc.)",
        example = "1"
    )
    @NotNull
    @Positive
    Short position,

    @ArraySchema(
        arraySchema = @Schema(
            description = "List of product IDs available in this category section",
            example = "[101, 102]"
        ),
        schema = @Schema(description = "Product ID", example = "101")
    )
    @NotNull
    @NotEmpty
    Collection<Long> products
) {
    public DayMenuCategorySimple toInput() {
        return new DayMenuCategorySimple(
            this.category,
            this.position,
            this.products
        );
    }
}
