package accrox.aros.api.application.usecases.product;


import java.util.HashSet;

import accrox.aros.api.application.dto.product.UpdateProductInput;
import accrox.aros.api.application.exceptions.product.BadUpdateProductException;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;

public class UpdateProductUseCase {
    private final ProductRepository repository;

    public UpdateProductUseCase(
            ProductRepository repository) {
        this.repository = repository;
    }

    public void execute(UpdateProductInput input) throws BadUpdateProductException {
        Product existingProduct;

        try {
            existingProduct = this.repository.findById(input.id()).get();
        } catch (Exception e) {
            throw new BadUpdateProductException("Product not found with id: " + input.id());
        }

        if (this.repository.existsByNameIgnoring(input.name(), new HashSet<>(input.categories()))) {
            throw new BadUpdateProductException("Product name already exists: " + input.name());
        }

        Product updatedProduct = this.mapInputToDomain(input, existingProduct);
        this.repository.update(updatedProduct);
    }

    private Product mapInputToDomain(UpdateProductInput input, Product existingProduct) {
        if (input.name() != null) {
            existingProduct.setName(input.name());
        }

        if (input.description() != null) {
            existingProduct.setDescription(input.description());
        }

        if (input.price() != null) {
            existingProduct.setPrice(input.price());
        }
        
        if (input.active() != null) {
            existingProduct.setActive(input.active());
        }

        if (input.preparationTime() != null) {
            existingProduct.setPreparationTime(input.preparationTime());
        }

        if (input.preparationArea() != null) {
            existingProduct.setPreparationArea(new Area(input.preparationArea(), null, null));
        }
        
        if (input.categories() != null) {
            existingProduct.setCategories(input.categories().stream().map(c -> {
                return new Category(c, null, null);
            }).toList());
        }

        return existingProduct;
    }
}