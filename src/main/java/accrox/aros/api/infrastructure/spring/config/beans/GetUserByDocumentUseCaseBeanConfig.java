package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import accrox.aros.api.application.usecases.user.GetUserByDocumentUseCase;
import accrox.aros.api.domain.repository.UserRepository;

@Controller
public class GetUserByDocumentUseCaseBeanConfig {
    @Bean
    public GetUserByDocumentUseCase getUserByDocumentUseCase(
            UserRepository repository
    ) {
        return new GetUserByDocumentUseCase(repository);
    }
}
