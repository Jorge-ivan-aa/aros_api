package accrox.aros.api.application.usecases.product;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import accrox.aros.api.application.dto.daymenu.CreateDayMenuInput;
import accrox.aros.api.application.dto.daymenu.DayMenuCategorySimple;
import accrox.aros.api.application.exceptions.category.CategoryNotFoundException;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.ProductRepository;

public class CreateDayMenuUseCase {
    private DaymenuRepository repository;

    private CategoryRepository categoryRepository;

    private ProductRepository productRepository;

    public CreateDayMenuUseCase(
        DaymenuRepository repository,
        CategoryRepository categoryRepository,
        ProductRepository productRepository
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void execute(CreateDayMenuInput input) throws
        ProductAlreadyExistsException,
        CategoryNotFoundException,
        ProductNotFoundException
    {
        Daymenu daymenu = new Daymenu();

        if (this.repository.existsByName(input.name())) {
            throw new ProductAlreadyExistsException();
        }

        this.checkForCategory(
            input.products().stream().map(p -> p.category()).toList()
        );

        this.checkForProducts(
            input.products().stream().flatMap(p -> p.products().stream()).collect(Collectors.toSet())
        );

        daymenu.setName(input.name());
        daymenu.setPrice(input.price());
        daymenu.setCreation(LocalDate.now());
        daymenu.setActive(true);
        daymenu.setPreparationTime(0);
        daymenu.setSubProducts(
            input.products().stream().map(this::createDayMenuCategory).toList()
        );

        this.repository.create(daymenu);
    }

    private void checkForCategory(Collection<Long> ids) throws CategoryNotFoundException {
        if (! this.categoryRepository.existsAllById(ids)) {
            throw new CategoryNotFoundException();
        }
    }

    private void checkForProducts(Set<Long> ids) throws ProductNotFoundException {
        if (! this.productRepository.existsAllById(ids)) {
            throw new ProductNotFoundException();
        }
    }

    private DayMenuCategory createDayMenuCategory(DayMenuCategorySimple input) {
        DayMenuCategory dmc = new DayMenuCategory();

        dmc.setPosition(input.position());
        dmc.setCategory(new Category(input.category(), null, null));
        dmc.setProducts(
            input.products().stream().map(id -> {
                Product product = new Product();
                product.setId(id);

                return product;
            }).toList()
        );

        return dmc;
    }
}
