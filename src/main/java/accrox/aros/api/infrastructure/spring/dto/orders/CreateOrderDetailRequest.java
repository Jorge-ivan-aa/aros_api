package accrox.aros.api.infrastructure.spring.dto.orders;

import java.util.Collection;

import accrox.aros.api.application.dto.order.CreateOrderDetailInput;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderDetailRequest (
    @NotNull @Positive Long product,
    @NotNull @Positive Integer quantity,
    String observations,
    @NotNull @NotEmpty Collection<Long> subProducts
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
