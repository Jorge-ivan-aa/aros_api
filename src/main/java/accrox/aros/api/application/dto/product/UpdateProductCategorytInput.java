package accrox.aros.api.application.dto.product;

import accrox.aros.api.application.dto.category.CategoryInput;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.Collection;

public record UpdateProductCategorytInput(
        String name,
        Collection<CategoryInput> categories
) {
}
