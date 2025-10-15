package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Daymenu;

public interface DaymenuRepository {
    public void create(Daymenu daymenu);

    public boolean existsByName(String name);
}