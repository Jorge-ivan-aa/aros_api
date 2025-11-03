package accrox.aros.api.application.usecases.product;

import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.dto.category.CategorySimpleOutput;
import accrox.aros.api.application.dto.product.ProductOutput;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;

public class GetProductByIdUseCase {
    private ProductRepository repository;

    public GetProductByIdUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductOutput execute(Long id) throws ProductNotFoundException {
        Product product = this.repository.findById(id).orElseThrow(() -> {
            return new ProductNotFoundException();
        });
        
        return new ProductOutput(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getActive(),
            product.getPreparationTime(),
            new GetAreaOutput(
                product.getPreparationArea().getId(),
                product.getPreparationArea().getName()
            ),
            product.getCategories().stream().map((c) -> {
                return new CategorySimpleOutput(c.getId(), c.getName());
            }).toList()
        );
    }
}
