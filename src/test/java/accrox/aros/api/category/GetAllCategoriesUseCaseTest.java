package accrox.aros.api.category;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import accrox.aros.api.application.usecases.category.GetAllCategoriesUseCase;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class GetAllCategoriesUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private GetAllCategoriesUseCase getAllCategoriesUseCase;

    @Test
    void whenCalled_thenReturnsAllCategories() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Cat1");
        Category c2 = new Category();
        c2.setId(2L);
        c2.setName("Cat2");
        List<Category> mocked = java.util.Arrays.asList(c1, c2);
        when(categoryRepository.findAll()).thenReturn(mocked);

        var result = getAllCategoriesUseCase.execute();

        assertEquals(2, result.size());
        assertEquals("Cat1", result.get(0).getName());
    }
}
