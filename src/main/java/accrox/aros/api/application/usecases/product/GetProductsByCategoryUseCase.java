package accrox.aros.api.application.usecases.product;

import java.util.List;
import java.util.Set;

import accrox.aros.api.application.dto.product.ProductSimpleOutput;
import accrox.aros.api.domain.repository.ProductRepository;

public class GetProductsByCategoryUseCase {
    private ProductRepository repository;
    
    public GetProductsByCategoryUseCase(ProductRepository repository) {
        this.repository = repository;
    }
    
    public List<ProductSimpleOutput> execute(Set<Long> categories) {
        return this.repository.findByCategories(categories)
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
            }).toList();
    }
}
