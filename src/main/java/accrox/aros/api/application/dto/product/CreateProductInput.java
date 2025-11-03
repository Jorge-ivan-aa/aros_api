package accrox.aros.api.application.dto.product;

public record CreateProductInput(
    String name,
    String description,
    Float price,
    Integer preparationTime,
    Long preparationArea
) {

}
