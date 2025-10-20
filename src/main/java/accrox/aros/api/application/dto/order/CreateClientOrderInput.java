package accrox.aros.api.application.dto.order;

import java.util.Collection;

public record CreateClientOrderInput (
    Collection<CreateOrderDetailInput> details
) {
    // 
}
