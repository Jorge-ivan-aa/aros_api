package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.OrderRepositoryJpa;

@Repository
public class OrderJpaAdapter implements OrderRepository {

    @Autowired
    private OrderRepositoryJpa orderRepositoryJpa;

}
