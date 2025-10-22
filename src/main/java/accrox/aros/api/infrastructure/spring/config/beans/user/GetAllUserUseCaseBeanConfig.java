package accrox.aros.api.infrastructure.spring.config.beans.user;

import accrox.aros.api.application.usecases.user.GetAllUserUseCase;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class GetAllUserUseCaseBeanConfig {
    @Bean
    public GetAllUserUseCase getAllUserUseCase(
            UserRepository repository
    ) {
        return new GetAllUserUseCase(repository);
    }
}
