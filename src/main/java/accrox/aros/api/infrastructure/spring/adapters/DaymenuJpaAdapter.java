package accrox.aros.api.infrastructure.spring.adapters;

import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.CategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.DayMenuCategoryEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.DaymenuEntity;
import accrox.aros.api.infrastructure.spring.jpa.entity.ProductEntity;
import accrox.aros.api.infrastructure.spring.jpa.repository.DaymenuRepositoryJpa;
import accrox.aros.api.infrastructure.spring.mappers.CategoryJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.DayMenuCategoryJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.DaymenuJpaMapper;
import accrox.aros.api.infrastructure.spring.mappers.ProductJpaMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DaymenuJpaAdapter implements DaymenuRepository {

    @Autowired
    private DaymenuRepositoryJpa daymenuRepositoryJpa;

    @Override
    @Transactional
    public void create(Daymenu daymenu) {
        DaymenuEntity menu = DaymenuJpaMapper.toEntity(
            daymenu,
            new LinkedList<>(),
            new LinkedList<>()
        );

        daymenu
            .getSubProducts()
            .forEach(dmc -> {
                Collection<ProductEntity> ps = dmc
                    .getProducts()
                    .stream()
                    .map(p -> ProductJpaMapper.toEntity(p, null, null))
                    .toList();

                CategoryEntity cat = CategoryJpaMapper.toEntity(
                    dmc.getCategory(),
                    null
                );

                menu.addProduct(
                    DayMenuCategoryJpaMapper.toEntity(dmc, menu, cat, ps)
                );
            });

        DaymenuEntity saved = this.daymenuRepositoryJpa.save(menu);

        this.daymenuRepositoryJpa.save(saved);
    }

    @Override
    public boolean existsByName(String name) {
        return this.daymenuRepositoryJpa.existsByName(name);
    }

    @Override
    public Collection<Long> findIdsIn(Collection<Long> ids) {
        return this.daymenuRepositoryJpa.findIdsIn(ids);
    }

    @Override
    @Transactional
    public List<Daymenu> findAll() {
        Iterable<DaymenuEntity> entities = this.daymenuRepositoryJpa.findAll();
        List<Daymenu> daymenus = new ArrayList<>();

        for (DaymenuEntity entity : entities) {
            Collection<Category> categories = entity
                .getCategories()
                .stream()
                .map(categoryEntity ->
                    CategoryJpaMapper.toDomain(categoryEntity, null)
                )
                .collect(Collectors.toList());

            daymenus.add(DaymenuJpaMapper.toDomain(entity, categories, null));
        }

        return daymenus;
    }

    @Override
    @Transactional
    public Optional<Daymenu> findCurrentDaymenu(LocalDate date) {
        Optional<DaymenuEntity> entity =
            this.daymenuRepositoryJpa.findByCreation(date);

        if (entity.isPresent()) {
            DaymenuEntity daymenuEntity = entity.get();

            // Load categories separately
            Collection<Category> categories = daymenuEntity
                .getCategories()
                .stream()
                .map(categoryEntity ->
                    CategoryJpaMapper.toDomain(categoryEntity, null)
                )
                .collect(Collectors.toList());

            // Load subproducts separately
            Collection<DayMenuCategory> subProducts = daymenuEntity
                .getProducts()
                .stream()
                .map(this::mapDayMenuCategoryEntityToDomain)
                .collect(Collectors.toList());

            Daymenu daymenu = DaymenuJpaMapper.toDomain(
                daymenuEntity,
                categories,
                subProducts
            );
            return Optional.of(daymenu);
        }

        return Optional.empty();
    }

    /**
     * Map DayMenuCategoryEntity to DayMenuCategory domain model with relations
     */
    private DayMenuCategory mapDayMenuCategoryEntityToDomain(
        DayMenuCategoryEntity entity
    ) {
        Category category = CategoryJpaMapper.toDomain(
            entity.getCategory(),
            null
        );

        Collection<Product> products = entity
            .getProducts()
            .stream()
            .map(productEntity ->
                ProductJpaMapper.toDomain(productEntity, null, null)
            )
            .collect(Collectors.toList());

        // Create a temporary Daymenu object with minimal information
        Daymenu tempDaymenu = new Daymenu();
        tempDaymenu.setId(entity.getDaymenu().getId());
        tempDaymenu.setName(entity.getDaymenu().getName());

        return DayMenuCategoryJpaMapper.toDomain(
            entity,
            tempDaymenu,
            category,
            products
        );
    }
}
