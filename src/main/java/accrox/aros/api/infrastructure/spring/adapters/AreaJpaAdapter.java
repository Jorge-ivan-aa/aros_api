package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.AreaRepositoryJpa;

@Repository
public class AreaJpaAdapter implements AreaRepository {

    @Autowired
    private AreaRepositoryJpa areaRepositoryJpa;
}
