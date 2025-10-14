package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Area;

import java.util.List;
import java.util.Optional;

public interface AreaRepository {

    Optional<Area> getAreaById(Long id);

    List<Area> getAreas();

    void saveArea(Area area);

    void deleteArea(Long id);

    Optional<Area> getAreaByName(String name);

    boolean existsByName(String name);

}