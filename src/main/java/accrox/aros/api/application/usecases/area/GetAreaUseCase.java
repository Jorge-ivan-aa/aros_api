package accrox.aros.api.application.usecases.area;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import jakarta.validation.ValidationException;

public class GetAreaUseCase {

    private AreaRepository repository;

    public GetAreaUseCase(AreaRepository repository){
        this.repository = repository;
    }

    public GetAreaOutput execute(String name){

        if(name == null || name.isBlank()){
            throw new ValidationException("The name is required");
        }

        if(!repository.existsByName(name)){
            throw new ValidationException("Are no exist");
        }

        Area area = repository.getAreaByName(name)
                .orElseThrow(() -> new ValidationException("Area not found"));

        return new GetAreaOutput(area.getId(), area.getName());

    }
}
