package accrox.aros.api.application.usecases.area;

import accrox.aros.api.application.dto.area.DeleteAreaInput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import jakarta.validation.ValidationException;

public class DeleteAreaUseCase {

    private final AreaRepository repository;

    public DeleteAreaUseCase(AreaRepository repository){
        this.repository = repository;
    }

    public void execute(DeleteAreaInput input)  {

        if(input.name() == null || input.name().isBlank()){
            throw new ValidationException("The name is required");
        }

        if(!repository.existsByName(input.name())){
            throw new ValidationException("Are no exist");
        }

        Area area = repository.getAreaByName(input.name())
                .orElseThrow(() -> new ValidationException("Area not found"));

        repository.deleteArea(area.getId());

    }
}
