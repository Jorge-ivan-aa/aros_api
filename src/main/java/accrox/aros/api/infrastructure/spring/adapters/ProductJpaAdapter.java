package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.ProductRepositoryJpa;

@Repository
public class ProductJpaAdapter implements ProductRepository {

    @Autowired
    private ProductRepositoryJpa productRepositoryJpa;

}
