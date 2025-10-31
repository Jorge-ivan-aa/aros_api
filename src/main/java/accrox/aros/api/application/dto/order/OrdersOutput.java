package accrox.aros.api.application.dto.order;

public record OrdersOutput(
        int id,
        String status,
        String date,
        String table,
        Float totalPrice
) {
}
