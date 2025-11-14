package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.user.DeleteUserUseCase;
import accrox.aros.api.application.usecases.user.GetAllUserUseCase;
import accrox.aros.api.application.usecases.user.GetUserByDocumentUseCase;
import accrox.aros.api.application.usecases.user.SaveUserUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserAreaUseCase;
import accrox.aros.api.application.usecases.user.UpdateUserUseCase;
import accrox.aros.api.domain.repository.AreaRepository;
import accrox.aros.api.domain.repository.UserRepository;
import accrox.aros.api.domain.service.PasswordService;

@Configuration
public class UserBeansConfig {
    @Bean
    public SaveUserUseCase saveUserUseCase(
            UserRepository userRepository,
            AreaRepository areaRepository,
            PasswordService passwordService) {
        return new SaveUserUseCase(userRepository, areaRepository, passwordService);
    }

    @Bean
    public DeleteUserUseCase deletUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    @Bean
    public GetAllUserUseCase getAllUserUseCase(
            UserRepository repository) {
        return new GetAllUserUseCase(repository);
    }

    @Bean
    public GetUserByDocumentUseCase getUserByDocumentUseCase(
            UserRepository repository) {
        return new GetUserByDocumentUseCase(repository);
    }

    @Bean
    public UpdateUserAreaUseCase updateUserAreaUseCase(
            UserRepository userRepository,
            AreaRepository arearepository) {
        return new UpdateUserAreaUseCase(userRepository, arearepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            UserRepository userRepository,
            PasswordService passwordService) {
        return new UpdateUserUseCase(userRepository, passwordService);
    }
}