package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.Table.CreateTableUseCase;
import accrox.aros.api.domain.repository.TableRepository;

@Configuration
public class TableBeansConfig {

    @Bean
    public CreateTableUseCase createTableUseCase(
            TableRepository tableRepository) {
        return new CreateTableUseCase(tableRepository);
    }

    @Bean
    public accrox.aros.api.application.usecases.Table.CreateTablesUseCase createTablesUseCase(
            CreateTableUseCase createTableUseCase) {
        return new accrox.aros.api.application.usecases.Table.CreateTablesUseCase(createTableUseCase);
    }

}
