package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.infrastructure.spring.jpa.repository.CategoryRepositoryJpa;

@Repository
public class CategoryJpaAdapter implements CategoryRepository {

    @Autowired
    private CategoryRepositoryJpa categoryRepositoryJpa;

}
