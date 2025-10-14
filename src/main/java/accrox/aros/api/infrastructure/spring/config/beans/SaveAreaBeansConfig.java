package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.area.SaveAreaUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaveAreaBeansConfig {
    @Bean
    public SaveAreaUseCase saveAreaUseCase(
            AreaRepository repository
    ){
        return new SaveAreaUseCase(repository);
    }
}
