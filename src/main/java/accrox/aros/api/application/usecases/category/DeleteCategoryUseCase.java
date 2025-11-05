package accrox.aros.api.application.usecases.category;

import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.domain.repository.CategoryRepository;

public class DeleteCategoryUseCase {
    private final CategoryRepository repository;

    public DeleteCategoryUseCase(CategoryRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) throws CategoryNotFoundException {
        if (! this.repository.existsById(id)) {
            throw new CategoryNotFoundException("The category doesn't exists");
        }

        this.repository.deleteById(id);
    }
}
