package accrox.aros.api.application.dto.order;

import java.util.List;

public record ClientDetailOrderOutput(
        Long id,
        Float total,
        String status,
        List<ProductDetailOutput> details
) {}