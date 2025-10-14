package accrox.aros.api.application.usecases.area;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import jakarta.validation.ValidationException;

import java.util.List;

public class GetAllAreaUseCase {

    private AreaRepository repository;

    public GetAllAreaUseCase(AreaRepository repository){
        this.repository = repository;
    }

    public List<GetAreaOutput> execute(){

        List<Area> areas = repository.getAreas();
        return areas.stream()
                .map(area -> new GetAreaOutput(area.getName()))
                .toList();
    }
}
