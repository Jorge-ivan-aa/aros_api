package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateAreaProductUseCaseBeansConfig {
    @Bean
    public UpdateAreaProductUseCase updateAreaProductUseCase(
            ProductRepository productRepository,
            AreaRepository arearepository
    ) {
        return new UpdateAreaProductUseCase(productRepository,arearepository);
    }
}
