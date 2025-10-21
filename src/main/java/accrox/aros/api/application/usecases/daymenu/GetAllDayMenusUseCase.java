package accrox.aros.api.application.usecases.daymenu;

import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.repository.DaymenuRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAllDayMenusUseCase {

    private final DaymenuRepository daymenuRepository;

    @Autowired
    public GetAllDayMenusUseCase(DaymenuRepository daymenuRepository) {
        this.daymenuRepository = daymenuRepository;
    }

    public List<Daymenu> execute() {
        return daymenuRepository.findAll();
    }
}
