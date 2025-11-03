package accrox.aros.api.infrastructure.spring.dto.order;

import accrox.aros.api.application.dto.order.CreateOrderDetailInput;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;

@Schema(
    description = "Request to create an order detail, which can be a regular product or a day menu with selections",
    example = """
    {
      "product": 27,
      "quantity": 1,
      "observations": "Sin picante",
      "subProducts": [101, 102, 103]
    }
    """
)
public record CreateOrderDetailRequest(
    @Schema(
        description = "ID of the product being ordered. For day menus, this should be the day menu product ID",
        example = "27"
    )
    @NotNull
    @Positive
    Long product,

    @Schema(
        description = "Quantity of the product to order",
        example = "1",
        minimum = "1"
    )
    @NotNull
    @Positive
    Integer quantity,

    @Schema(
        description = "Additional observations or special requests for this order detail",
        example = "Sin picante, bien cocido"
    )
    String observations,

    @ArraySchema(
        schema = @Schema(
            description = "List of subproduct IDs. For day menus, these represent the selected product IDs from each category",
            example = "[2, 5, 8, 13]"
        )
    )
    Collection<Long> subProducts
) {
    public CreateOrderDetailInput toInput() {
        return new CreateOrderDetailInput(
            this.product,
            this.quantity,
            this.observations,
            this.subProducts != null ? this.subProducts : List.of()
        );
    }
}
