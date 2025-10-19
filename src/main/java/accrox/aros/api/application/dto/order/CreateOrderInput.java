package accrox.aros.api.application.dto.order;

import java.util.Collection;

public record CreateOrderInput (
    Long table,
    Collection<CreateClientOrderInput> clientOrders
) {
    // 
}
