package accrox.aros.api.product;

import accrox.aros.api.application.dto.category.CategoryInput;
import accrox.aros.api.application.dto.product.UpdateProductCategorytInput;
import accrox.aros.api.application.usecases.product.UpdateProductCategoriesUseCase;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import accrox.aros.api.domain.model.Category;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProductCategoriesUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UpdateProductCategoriesUseCase updateProductCategoriesUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_product_and_categories_exist_then_update_categories() {
        String productName = "Pizza";
        String categoryName = "Italian Food";

        Product product = new Product();
        product.setName(productName);

        Category category = new Category();
        category.setName(categoryName);

        CategoryInput categoryInput = new CategoryInput(categoryName);
        Collection<CategoryInput> categoryInputs = List.of(categoryInput);

        UpdateProductCategorytInput input =
                new UpdateProductCategorytInput(productName, categoryInputs);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        updateProductCategoriesUseCase.execute(input);

        verify(productRepository).UpdateProductCategories(product);
        assertEquals(List.of(category), product.getCategories());
    }
}