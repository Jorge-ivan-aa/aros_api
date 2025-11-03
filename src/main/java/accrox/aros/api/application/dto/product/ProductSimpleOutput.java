package accrox.aros.api.application.dto.product;

public record ProductSimpleOutput (
    Long id,
    String name,
    String description,
    Float price,
    Boolean active,
    Integer preparationTime,
    String preparationArea
) {

}
