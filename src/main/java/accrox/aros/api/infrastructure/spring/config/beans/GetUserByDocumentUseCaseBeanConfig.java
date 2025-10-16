package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.product.CreateProductUseCase;
import accrox.aros.api.application.usecases.user.GetUserByDocumentUseCase;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class GetUserByDocumentUseCaseBeanConfig {
    @Bean
    public GetUserByDocumentUseCase getUserByDocumentUseCase(
            UserRepository repository
    ) {
        return new GetUserByDocumentUseCase(repository);
    }
}
