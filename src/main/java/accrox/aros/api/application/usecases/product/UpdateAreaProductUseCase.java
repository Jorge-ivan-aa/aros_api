package accrox.aros.api.application.usecases.product;

import java.util.Optional;

import accrox.aros.api.application.dto.area.GetAreaInput;
import accrox.aros.api.application.dto.product.UpdateProductAreaInput;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.infrastructure.spring.jpa.entity.AreaEntity;
import jakarta.validation.ValidationException;

public class UpdateAreaProductUseCase {

    private final ProductRepository productRepository;
    private final AreaRepository areaRepository;

    public UpdateAreaProductUseCase(ProductRepository productRepository, AreaRepository areaRepository) {
        this.productRepository = productRepository;
        this.areaRepository = areaRepository;
    }

    public void execute(UpdateProductAreaInput input) {
        if (input.name() == null || input.name().isBlank()) {
            throw new IllegalArgumentException("The name is required");
        }

        Optional<Product> productOpt = productRepository.findByName(input.name());
        if (productOpt.isEmpty()) {
            throw new ValidationException("No user found with the provided name");
        }

        GetAreaInput areaDto = input.area();

        Product product = productOpt.get();

        Area area = areaRepository.getAreaByName(areaDto.name())
                .orElseThrow(() -> new ValidationException(
                        "Area not found: " + input.name()));
        product.setPreparationArea(area);

        productRepository.updateProductArea(product);
    }
}
