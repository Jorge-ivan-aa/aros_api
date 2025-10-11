package accrox.aros.api.application.usecases.category;

import accrox.aros.api.application.dto.category.CreateCategoryDto;
import accrox.aros.api.application.exceptions.ValidationException;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;

public class CreateCategoryUseCase {

    private CategoryRepository repository;

    public CreateCategoryUseCase(CategoryRepository repository) {
        this.repository = repository;
    }

    public void execute(CreateCategoryDto dto) throws ValidationException {
        Category category = new Category();

        if (dto.name().isBlank()) {
            throw new ValidationException("Usted esta bien pendejo");
        }

        category.setName(dto.name());

        repository.create(category);
    }
}
