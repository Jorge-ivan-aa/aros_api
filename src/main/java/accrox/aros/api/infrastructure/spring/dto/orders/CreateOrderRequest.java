package accrox.aros.api.infrastructure.spring.dto.orders;

import accrox.aros.api.application.dto.order.CreateOrderInput;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Collection;

@Schema(description = "Request to create a new order with multiple client orders", example = """
    {
      "table": 1,
      "responsible": "1001",
      "clientOrders": [
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
        },
        {
          "details": [
            {
              "product": 2,
              "quantity": 1,
              "observations": "Término medio",
              "subProducts": [103]
            }
          ]
        }
      ]
    }
    """)
public record CreateOrderRequest(
    @Schema(description = "ID of the table where the order is placed", example = "1") @NotNull @Positive Long table,

    @Schema(description = "Document ID of the responsible user (employee) for this order", example = "1001") @NotNull String responsible,

    @ArraySchema(schema = @Schema(description = "List of client orders, each representing an individual client's order", implementation = CreateClientOrderRequest.class)) @Valid @NotEmpty Collection<CreateClientOrderRequest> clientOrders) {
  public CreateOrderInput toInput() {
    return new CreateOrderInput(
        this.table,
        this.responsible,
        this.clientOrders.stream()
            .map(co -> co.toInput())
            .toList());
  }
}
