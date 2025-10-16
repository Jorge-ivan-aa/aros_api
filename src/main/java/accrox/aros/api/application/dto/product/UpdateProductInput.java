package accrox.aros.api.application.dto.product;

public record UpdateProductInput(
        String currentName,
        String newName,
        String description,
        Float price,
        Integer preparationTime) {

}
