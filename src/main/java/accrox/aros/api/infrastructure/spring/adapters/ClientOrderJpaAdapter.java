package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.ClientOrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.ClientOrderRepositoryJpa;

@Repository
public class ClientOrderJpaAdapter implements ClientOrderRepository {

    @Autowired
    private ClientOrderRepositoryJpa clientOrderRepositoryJpa;

}
