package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.area.DeleteAreaUseCase;
import accrox.aros.api.application.usecases.area.GetAllAreaUseCase;
import accrox.aros.api.application.usecases.area.GetAreaUseCase;
import accrox.aros.api.application.usecases.area.SaveAreaUseCase;
import accrox.aros.api.application.usecases.product.UpdateAreaProductUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.ProductRepository;

@Configuration
public class AreaBeansConfig {
    @Bean
    public GetAllAreaUseCase getAllAreaUseCase(
            AreaRepository repository) {
        return new GetAllAreaUseCase(repository);
    }

    @Bean
    public GetAreaUseCase getAreaUseCase(
            AreaRepository repository) {
        return new GetAreaUseCase(repository);
    }

    @Bean
    public SaveAreaUseCase saveAreaUseCase(
            AreaRepository repository) {
        return new SaveAreaUseCase(repository);
    }

    @Bean
    public UpdateAreaProductUseCase updateAreaProductUseCase(
            ProductRepository productRepository,
            AreaRepository arearepository) {
        return new UpdateAreaProductUseCase(productRepository, arearepository);
    }

    @Bean
    public DeleteAreaUseCase deleteAreaUseCase(
            AreaRepository repository) {
        return new DeleteAreaUseCase(repository);
    }
}
