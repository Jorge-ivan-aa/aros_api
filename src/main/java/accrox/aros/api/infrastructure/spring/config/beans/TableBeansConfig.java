package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import accrox.aros.api.domain.repository.TableRepository;

@Configuration
public class TableBeansConfig {

    @Bean
    public CreateTableUseCase createTableUseCase(
        TableRepository tableRepository
    ) {
        return new CreateTableUseCase(tableRepository);
    }

}
