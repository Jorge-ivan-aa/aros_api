package accrox.aros.api.application.dto.order;

import java.util.Collection;

public record CreateOrderDetailInput (
    Long product,
    Integer quantity,
    String observations,
    Collection<Long> subProducts
) {
    // 
}
