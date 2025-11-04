package accrox.aros.api.application.dto.order;

public record ProductDetailOutput(
        Long id,
        String name,
        Float price,
        Integer quantity,
        String observations
) {}