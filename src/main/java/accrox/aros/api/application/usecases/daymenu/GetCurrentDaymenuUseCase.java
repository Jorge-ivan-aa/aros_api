package accrox.aros.api.application.usecases.daymenu;

import accrox.aros.api.application.dto.daymenu.DaymenuOutput;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.infrastructure.spring.mappers.DaymenuOutputMapper;
import java.time.LocalDate;
import java.util.Optional;

public class GetCurrentDaymenuUseCase {

    private final DaymenuRepository daymenuRepository;

    public GetCurrentDaymenuUseCase(DaymenuRepository daymenuRepository) {
        this.daymenuRepository = daymenuRepository;
    }

    public Optional<DaymenuOutput> execute() {
        LocalDate today = LocalDate.now();
        Optional<Daymenu> daymenu = daymenuRepository.findCurrentDaymenu(today);
        return daymenu.map(DaymenuOutputMapper::toOutput);
    }
}
