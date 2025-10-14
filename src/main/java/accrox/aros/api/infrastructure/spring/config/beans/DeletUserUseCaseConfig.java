package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.user.DeleteUserUseCase;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeletUserUseCaseConfig {
    @Bean
    public DeleteUserUseCase deletUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

}

