package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.CategoryRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.CategoryJpaMapper;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepositoryJpa
            .findByName(name)
            .map(entity -> CategoryJpaMapper.toDomain(entity, null));
    }

    @Override
    public boolean existsAllById(Collection<Long> ids) {
        return this.categoryRepositoryJpa.existsAllByIdIn(
            new LinkedHashSet<>(ids),
            ids.size()
        );
    }

    @Override
    public List<Category> findAll() {
        Iterable<CategoryEntity> entities =
            this.categoryRepositoryJpa.findAll();
        List<Category> categories = StreamSupport.stream(
            entities.spliterator(),
            false
        )
            .map(entity -> CategoryJpaMapper.toDomain(entity, null))
            .collect(Collectors.toList());

        return categories;
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepositoryJpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.categoryRepositoryJpa.existsById(id);
    }
}
