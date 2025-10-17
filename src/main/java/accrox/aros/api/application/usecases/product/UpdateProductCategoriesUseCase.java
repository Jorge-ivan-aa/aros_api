package accrox.aros.api.application.usecases.product;

import accrox.aros.api.application.dto.category.CategoryInput;
import accrox.aros.api.application.dto.product.UpdateProductCategorytInput;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import jakarta.validation.ValidationException;

import java.util.Collection;
import java.util.Optional;

public class UpdateProductCategoriesUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public UpdateProductCategoriesUseCase(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void execute(UpdateProductCategorytInput input) {

        if (input.name() == null || input.name().isBlank()) {
            throw new IllegalArgumentException("The name is required");
        }

        Optional<Product> productOpt = productRepository.findByName(input.name());
        if (productOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided name");
        }

        Collection<CategoryInput> categoryInputs = input.categories();
        if (categoryInputs == null || categoryInputs.isEmpty()) {
            throw new ValidationException("At least one category must be provided");
        }

        Collection<Category> categories = categoryInputs.stream()
                .map(catInput -> categoryRepository.findByName(catInput.name())
                        .orElseThrow(() -> new ValidationException(
                                "Category not found: " + catInput.name())))
                .toList();

        Product product = productOpt.get();

        product.setCategories(categories);

        productRepository.UpdateProductCategories(product);
    }
}
