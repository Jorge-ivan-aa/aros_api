package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Daymenu;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DaymenuRepository {
    public void create(Daymenu daymenu);

    public boolean existsByName(String name);

    public Collection<Long> findIdsIn(Collection<Long> ids);

    public List<Daymenu> findAll();

    public Optional<Daymenu> findCurrentDaymenu(LocalDate date);

    public boolean existsByDate(LocalDate date);
}
