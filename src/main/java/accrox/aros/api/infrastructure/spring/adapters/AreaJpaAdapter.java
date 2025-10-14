package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.Area;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import accrox.aros.api.infrastructure.spring.mappers.AreaJpaMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.AreaRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Repository
public class AreaJpaAdapter implements AreaRepository {

    @Autowired
    private AreaRepositoryJpa areaRepositoryJpa;

    @Override
    public Optional<Area> getAreaById(Long id) {
        return Optional.of(AreaJpaMapper.toDomain(areaRepositoryJpa.findById(id).get(),null));
    }

    @Override
    public List<Area> getAreas() {
        List<AreaEntity> entities = (List<AreaEntity>) areaRepositoryJpa.findAll();
        return entities.stream().
                map(entity -> AreaJpaMapper.toDomain(entity,null))
                .toList();
    }

    @Override
    public void saveArea(Area area) {
        AreaEntity entity = AreaJpaMapper.toEntity(area,null);
        this.areaRepositoryJpa.save(entity);
    }

    @Override
    @Transactional
    public void deleteArea(Long id) {
        this.areaRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<Area> getAreaByName(String name) {
        return Optional.of(AreaJpaMapper.toDomain(areaRepositoryJpa.getByName(name).get(),null));
    }

    @Override
    public boolean existsByName(String name) {
        return areaRepositoryJpa.existsByName(name);
    }
}
