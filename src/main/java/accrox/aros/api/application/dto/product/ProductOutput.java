package accrox.aros.api.application.dto.product;

import java.util.Collection;

import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.dto.category.CategorySimpleOutput;

public record ProductOutput(
    Long id,
    String name,
    String description,
    Float price,
    Boolean active,
    Integer preparationTime,
    GetAreaOutput preparationArea,
    Collection<CategorySimpleOutput> categories
) {
    // 
}
