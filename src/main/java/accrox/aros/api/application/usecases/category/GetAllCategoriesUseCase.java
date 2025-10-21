package accrox.aros.api.application.usecases.category;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAllCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    @Autowired
    public GetAllCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> execute() {
        return categoryRepository.findAll();
    }
}
