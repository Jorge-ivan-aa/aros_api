package accrox.aros.api.application.usecases.product;

import accrox.aros.api.application.dto.product.ProductSimpleOutput;
import accrox.aros.api.domain.repository.ProductRepository;
import java.util.List;

public class GetAllProductsUseCase {

    private final ProductRepository productRepository;

    public GetAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductSimpleOutput> execute() {
        return productRepository.findAll()
            .stream()
            .map(p -> {
                return new ProductSimpleOutput(
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getActive(),
                    p.getPreparationTime(),
                    p.getPreparationArea() != null ? p.getPreparationArea().getName() : null
                );
            })
            .toList();
    }

    // public List<Product> execute() {
    //     return productRepository.findAllWithRelations();
    // }
}
