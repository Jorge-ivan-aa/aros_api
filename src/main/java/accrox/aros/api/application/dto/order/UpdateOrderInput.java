package accrox.aros.api.application.dto.order;

import accrox.aros.api.domain.model.enums.OrderStatus;

public record UpdateOrderInput(
        Long id,
        OrderStatus status,
        Long table,
        Long responsible) {
    //
}
