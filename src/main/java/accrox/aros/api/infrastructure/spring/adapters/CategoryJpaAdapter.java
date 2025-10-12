package accrox.aros.api.infrastructure.spring.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.CategoryRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.CategoryJpaMapper;

@Repository
public class CategoryJpaAdapter implements CategoryRepository {
    @Autowired
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @Override
    public Category create(Category category) {
        CategoryEntity entity = CategoryJpaMapper.toEntity(category, null);
        CategoryEntity saved = this.categoryRepositoryJpa.save(entity);

        return CategoryJpaMapper.toDomain(saved, null);
    }

    @Override
    public boolean existsByName(String name) {
        return this.categoryRepositoryJpa.existsByName(name);
    }
}
