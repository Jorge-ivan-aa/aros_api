package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.area.GetAllAreaUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAllAreaBeansConfig {
    @Bean
    public GetAllAreaUseCase getAllAreaUseCase(
            AreaRepository repository
    ){
        return new GetAllAreaUseCase(repository);
    }
}
