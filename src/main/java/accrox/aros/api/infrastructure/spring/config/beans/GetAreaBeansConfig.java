package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.area.GetAreaUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAreaBeansConfig {
    @Bean
    public GetAreaUseCase getAreaUseCase(
            AreaRepository repository
    ){
        return new GetAreaUseCase(repository);
    }
}
