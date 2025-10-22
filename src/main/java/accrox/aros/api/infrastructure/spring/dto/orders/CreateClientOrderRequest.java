package accrox.aros.api.infrastructure.spring.dto.orders;

import java.util.Collection;

import accrox.aros.api.application.dto.order.CreateClientOrderInput;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(
    description = "Represents an individual client's order with multiple order details",
    example = """
    {
      "details": [
        {
          "product": 1,
          "quantity": 2,
          "observations": "Sin cebolla, bien cocido",
          "subProducts": [101, 102]
        },
        {
          "product": 3,
          "quantity": 1,
          "observations": "Poco azúcar",
          "subProducts": []
        }
      ]
    }
    """
)
public record CreateClientOrderRequest(
    @ArraySchema(
        schema = @Schema(
            description = "List of order details for this client",
            implementation = CreateOrderDetailRequest.class
        )
    )
    @NotNull
    @NotEmpty
    @Valid
    Collection<CreateOrderDetailRequest> details
) {
    public CreateClientOrderInput toInput() {
        return new CreateClientOrderInput(
            details
                .stream()
                .map(d -> d.toInput())
                .toList()
        );
    }
}
