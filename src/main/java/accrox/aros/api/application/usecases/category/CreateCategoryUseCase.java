package accrox.aros.api.application.usecases.category;

import accrox.aros.api.application.dto.category.CreateCategoryInput;
import accrox.aros.api.application.dto.category.CreateCategoryOutput;
import accrox.aros.api.application.exceptions.category.CategoryAlreadyExistsException;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;

public class CreateCategoryUseCase {
    private CategoryRepository repository;

    public CreateCategoryUseCase(CategoryRepository repository) {
        this.repository = repository;
    }

    public CreateCategoryOutput execute(CreateCategoryInput dto) throws CategoryAlreadyExistsException {
        if (repository.existsByName(dto.name())) {
            throw new CategoryAlreadyExistsException();
        }

        Category category = new Category();
        category.setName(dto.name());

        Category saved = repository.create(category);
        CreateCategoryOutput created = new CreateCategoryOutput(saved.getName());

        return created;
    }
}
