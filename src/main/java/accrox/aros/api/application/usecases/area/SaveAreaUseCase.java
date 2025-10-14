package accrox.aros.api.application.usecases.area;

import accrox.aros.api.application.dto.area.SaveAreaInput;
import accrox.aros.api.application.exceptions.ValidationException;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;

public class SaveAreaUseCase {

    private final AreaRepository repository;

    public SaveAreaUseCase(AreaRepository repository) {
        this.repository = repository;
    }

    public void execute(SaveAreaInput areaInput) throws ValidationException {

        if (areaInput.name().isBlank()) {
            throw new ValidationException("The area name cannot be blank");
        }

        if (repository.existsByName(areaInput.name())) {
            throw new ValidationException("An area with this name already exists");
        }
        Area area = new Area();
        area.setName(areaInput.name());

        repository.saveArea(area);
    }
}
