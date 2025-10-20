package accrox.aros.api.infrastructure.spring.dto.orders;

import java.util.Collection;

import accrox.aros.api.application.dto.order.CreateClientOrderInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateClientOrderRequest (
    @NotNull @NotEmpty @Valid
    Collection<CreateOrderDetailRequest> details
) {
    public CreateClientOrderInput toInput() {
        return new CreateClientOrderInput(
            details.stream().map(d -> d.toInput()).toList()
        );
    }
}
