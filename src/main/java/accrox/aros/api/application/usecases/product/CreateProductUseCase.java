package accrox.aros.api.application.usecases.product;

import accrox.aros.api.application.dto.product.CreateProductInput;
import accrox.aros.api.application.exceptions.product.ProductAlreadyExistsException;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;

public class CreateProductUseCase {
    private final ProductRepository repository;

    public CreateProductUseCase(
        ProductRepository repository
    ) {
        this.repository = repository;
    }

    public void execute(CreateProductInput input) throws ProductAlreadyExistsException {
        if (this.repository.existsByName(input.name())) {
            throw new ProductAlreadyExistsException();
        }

        Product product = this.mapInputToDomain(input);

        this.repository.create(product);
    }

    private Product mapInputToDomain(CreateProductInput input) {
        Product product = new Product();

        product.setName(input.name());
        product.setDescription(input.description());
        product.setPrice(input.price());
        product.setPreparationTime(input.preparationTime());
        product.setActive(true);
        product.setPreparationArea(new Area(input.preparationArea(), null, null));

        return product;
    }
}
