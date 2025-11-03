package accrox.aros.api.application.usecases.product;

import java.util.List;

import accrox.aros.api.application.dto.product.ProductSimpleOutput;
import accrox.aros.api.domain.repository.ProductRepository;

public class GetNoDayMenuProducts {
    private final ProductRepository productRepository;

    public GetNoDayMenuProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductSimpleOutput> execute() {
        return productRepository.getNoDayMenuProducts()
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
}
