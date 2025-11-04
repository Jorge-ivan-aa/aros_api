package accrox.aros.api.application.dto.order;

import java.util.List;

public record DetailOrderOutput(
        Long id,
        String status,
        String takedAt,
        Float total,
        String tableName,
        String responsibleName,
        List<ClientDetailOrderOutput> clientOrders
) {}