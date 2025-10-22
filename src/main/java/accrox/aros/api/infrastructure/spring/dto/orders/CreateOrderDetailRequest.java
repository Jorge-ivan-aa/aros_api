package accrox.aros.api.infrastructure.spring.dto.orders;

import java.util.Collection;

import accrox.aros.api.application.dto.order.CreateOrderDetailInput;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(
    description = "Represents a single order detail with product information and customization",
    example = """
    {
      "product": 1,
      "quantity": 2,
      "observations": "Sin cebolla, bien cocido",
      "subProducts": [101, 102]
    }
    """
)
public record CreateOrderDetailRequest(
    @Schema(description = "ID of the product being ordered", example = "1")
    @NotNull
    @Positive
    Long product,

    @Schema(description = "Quantity of the product to order", example = "2")
    @NotNull
    @Positive
    Integer quantity,

    @Schema(
        description = "Special instructions or observations for the product preparation",
        example = "Sin cebolla, bien cocido",
        nullable = true
    )
    String observations,

    @ArraySchema(
        arraySchema = @Schema(
            description = "List of sub-product IDs for additional customization",
            example = "[101, 102]"
        ),
        schema = @Schema(description = "Sub-product ID", example = "101")
    )
    @Size(min = 1)
    Collection<Long> subProducts
) {
    public CreateOrderDetailInput toInput() {
        return new CreateOrderDetailInput(
            this.product,
            this.quantity,
            this.observations,
            this.subProducts
        );
    }
}
