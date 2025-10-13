package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.auth.User.SaveUserUseCase;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public SaveUserUseCase saveUserUseCase(UserRepository userRepository) {
        return new SaveUserUseCase(userRepository);
    }
}
