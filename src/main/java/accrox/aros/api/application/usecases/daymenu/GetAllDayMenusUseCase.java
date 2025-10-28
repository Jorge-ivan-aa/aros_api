package accrox.aros.api.application.usecases.daymenu;

import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.repository.DaymenuRepository;
import java.util.List;

public class GetAllDayMenusUseCase {

    private final DaymenuRepository daymenuRepository;

    public GetAllDayMenusUseCase(DaymenuRepository daymenuRepository) {
        this.daymenuRepository = daymenuRepository;
    }

    public List<Daymenu> execute() {
        return daymenuRepository.findAll();
    }
}
