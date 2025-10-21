package accrox.aros.api.application.usecases.product;

import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAllProductsUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public GetAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> execute() {
        return productRepository.findAll();
    }
}
