package accrox.aros.api.application.usecases.product;

import accrox.aros.api.application.dto.product.UpdateProductInput;
import accrox.aros.api.application.exceptions.product.BadUpdateProductException;
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
            existingProduct = this.repository.findByName(input.currentName()).get();
        } catch (Exception e) {
            throw new BadUpdateProductException("Product not found with name: " + input.currentName());
        }

        if (input.newName() != null &&
                !input.newName().equals(input.currentName()) &&
                this.repository.existsByName(input.newName())) {
            throw new BadUpdateProductException("Product name already exists: " + input.newName());
        }

        Product updatedProduct = this.mapInputToDomain(input, existingProduct);
        this.repository.update(updatedProduct);
    }

    private Product mapInputToDomain(UpdateProductInput input, Product existingProduct) {
        if (input.newName() != null) {
            existingProduct.setName(input.newName());
        }
        if (input.description() != null) {
            existingProduct.setDescription(input.description());
        }
        if (input.price() != null) {
            existingProduct.setPrice(input.price());
        }
        if (input.preparationTime() != null) {
            existingProduct.setPreparationTime(input.preparationTime());
        }
        return existingProduct;
    }
}