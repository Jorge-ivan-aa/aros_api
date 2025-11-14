package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.application.usecases.product.GetAllProductsUseCase;
import accrox.aros.api.application.usecases.product.GetNoDayMenuProducts;
import accrox.aros.api.application.usecases.product.GetProductByIdUseCase;
import accrox.aros.api.application.usecases.product.GetProductsByCategoryUseCase;
import accrox.aros.api.application.usecases.product.UpdateProductCategoriesUseCase;
import accrox.aros.api.application.usecases.product.UpdateProductUseCase;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.application.usecases.order.GetTopSellingProductsUseCase;
import accrox.aros.api.domain.repository.CategoryRepository;
import accrox.aros.api.domain.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeansConfig {

    @Bean
    public CreateProductUseCase createProductUseCase(
            ProductRepository repository) {
        return new CreateProductUseCase(repository);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(
            ProductRepository repository) {
        return new UpdateProductUseCase(repository);
    }

    @Bean
    public GetAllProductsUseCase getAllProductsUseCase(
            ProductRepository repository) {
        return new GetAllProductsUseCase(repository);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase(
            ProductRepository repository) {
        return new GetProductByIdUseCase(repository);
    }

    @Bean
    public GetProductsByCategoryUseCase getProductsByCategoryUseCase(
            ProductRepository repository) {
        return new GetProductsByCategoryUseCase(repository);
    }

    @Bean
    public GetNoDayMenuProducts getNoDayMenuProducts(
            ProductRepository repository) {
        return new GetNoDayMenuProducts(repository);
    }

    @Bean
    public GetTopSellingProductsUseCase getTopSellingProductsUseCase(
            OrderRepository orderRepository,
            ProductRepository productRepository) {
        return new GetTopSellingProductsUseCase(orderRepository, productRepository);
    }

    @Bean
    public UpdateProductCategoriesUseCase updateProductCategoriesUseCase(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        return new UpdateProductCategoriesUseCase(productRepository, categoryRepository);
    }
}
