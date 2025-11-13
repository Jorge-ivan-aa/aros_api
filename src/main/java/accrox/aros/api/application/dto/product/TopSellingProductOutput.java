package accrox.aros.api.application.dto.product;

import java.util.List;

public record TopSellingProductOutput(
    String name,
    List<String> categories,
    Long soldQuantity
) {}