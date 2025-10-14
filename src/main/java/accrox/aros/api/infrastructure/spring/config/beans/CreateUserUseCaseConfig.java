package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateUserUseCaseConfig {
    @Bean
    public SaveUserUseCase saveUserUseCase(
            UserRepository userRepository,
            PasswordService passwordService) {
        return new SaveUserUseCase(userRepository, passwordService);
    }
}