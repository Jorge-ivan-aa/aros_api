package accrox.aros.api.domain.repository;

import java.util.Collection;

import accrox.aros.api.domain.model.Daymenu;

public interface DaymenuRepository {
    public void create(Daymenu daymenu);

    public boolean existsByName(String name);

    public Collection<Long> findIdsIn(Collection<Long> ids);
}