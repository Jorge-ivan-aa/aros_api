package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.area.DeleteAreaUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteAreaBeansConfig {
    @Bean
    public DeleteAreaUseCase deleteAreaUseCase(
            AreaRepository repository
    ){
        return new DeleteAreaUseCase(repository);
    }
}
