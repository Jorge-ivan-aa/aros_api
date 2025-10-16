package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.ProductRepository;

@Configuration
public class UpdateAreaProductUseCaseBeansConfig {
    @Bean
    public UpdateAreaProductUseCase updateAreaProductUseCase(
            ProductRepository productRepository,
            AreaRepository arearepository) {
        return new UpdateAreaProductUseCase(productRepository, arearepository);
    }
}
