package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.DaymenuRepositoryJpa;

@Repository
public class DaymenuJpaAdapter implements DaymenuRepository {

    @Autowired
    private DaymenuRepositoryJpa daymenuRepositoryJpa;

}
