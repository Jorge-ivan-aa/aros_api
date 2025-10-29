package accrox.aros.api.application.usecases.product;

import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import java.util.List;

public class GetAllProductsUseCase {

    private final ProductRepository productRepository;

    public GetAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> execute() {
        return productRepository.findAll();
    }
}
