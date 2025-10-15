package accrox.aros.api.infrastructure.spring.dto.orders;

import java.util.Collection;

import accrox.aros.api.application.dto.order.CreateOrderInput;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest (
    @NotNull @Positive
    Long table,
    @NotNull @NotEmpty
    Collection<CreateClientOrderRequest> clientOrders
) {
    public CreateOrderInput toInput() {
        return new CreateOrderInput(
            this.table,
            this.clientOrders.stream().map(co -> co.toInput()).toList()
        );
    }
}
