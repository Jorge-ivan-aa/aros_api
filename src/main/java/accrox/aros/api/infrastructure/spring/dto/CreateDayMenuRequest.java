package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.daymenu.CreateDayMenuInput;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Collection;

@Schema(
    description = "Request to create a day menu with organized categories and products",
    example = """
    {
      "name": "Menú del Día - 2024-01-15",
      "price": 18000,
      "products": [
        {
          "category": 1,
          "position": 1,
          "products": [101, 102]
        },
        {
          "category": 2,
          "position": 2,
          "products": [201, 202, 203]
        },
        {
          "category": 3,
          "position": 3,
          "products": [301, 302, 303]
        },
        {
          "category": 4,
          "position": 4,
          "products": [401, 402]
        },
        {
          "category": 5,
          "position": 5,
          "products": [501, 502]
        }
      ]
    }
    """
)
public record CreateDayMenuRequest(
    @Schema(
        description = "Name of the day menu",
        example = "Menú del Día - 2024-01-15"
    )
    @NotNull
    @NotBlank
    String name,

    @Schema(description = "Price of the day menu", example = "18000")
    @NotNull
    @PositiveOrZero
    Float price,

    @ArraySchema(
        schema = @Schema(
            description = "List of menu categories with their products",
            implementation = CreateDayMenuItemRequest.class
        )
    )
    @Valid
    @NotEmpty
    Collection<CreateDayMenuItemRequest> products
) {
    public CreateDayMenuInput toInput() {
        return new CreateDayMenuInput(
            this.name,
            this.price,
            this.products.stream()
                .map(p -> p.toInput())
                .toList()
        );
    }
}
