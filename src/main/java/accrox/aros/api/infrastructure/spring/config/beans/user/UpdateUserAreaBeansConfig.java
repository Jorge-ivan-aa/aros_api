package accrox.aros.api.infrastructure.spring.config.beans.user;

import accrox.aros.api.application.usecases.user.UpdateUserAreaUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateUserAreaBeansConfig {
    @Bean
    public UpdateUserAreaUseCase updateUserAreaUseCase(
            UserRepository userRepository,
            AreaRepository arearepository
    ) {
        return new UpdateUserAreaUseCase(userRepository,arearepository);
    }
}
