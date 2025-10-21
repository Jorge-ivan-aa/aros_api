package accrox.aros.api.domain.repository;

import java.util.Collection;
import java.util.List;

import accrox.aros.api.domain.model.Daymenu;

public interface DaymenuRepository {
    public void create(Daymenu daymenu);

    public boolean existsByName(String name);

    public Collection<Long> findIdsIn(Collection<Long> ids);
    
    public List<Daymenu> findAll();
}