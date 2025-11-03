package accrox.aros.api.infrastructure.spring.config.beans;

import accrox.aros.api.application.usecases.table.CreateTableUseCase;
import accrox.aros.api.application.usecases.table.CreateTablesUseCase;
import accrox.aros.api.application.usecases.table.GetTablesStatusUseCase;
import accrox.aros.api.application.usecases.table.UpdateTablesCountUseCase;
import accrox.aros.api.domain.repository.TableRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TableBeansConfig {

    @Bean
    public CreateTableUseCase createTableUseCase(
        TableRepository tableRepository
    ) {
        return new CreateTableUseCase(tableRepository);
    }

    @Bean
    public CreateTablesUseCase createTablesUseCase(
        CreateTableUseCase createTableUseCase
    ) {
        return new CreateTablesUseCase(createTableUseCase);
    }

    @Bean
    public UpdateTablesCountUseCase updateTablesCountUseCase(
        TableRepository tableRepository,
        CreateTableUseCase createTableUseCase
    ) {
        return new UpdateTablesCountUseCase(
            tableRepository,
            createTableUseCase
        );
    }

    @Bean
    public GetTablesStatusUseCase getTablesStatusUseCase(
        TableRepository tableRepository
    ) {
        return new GetTablesStatusUseCase(tableRepository);
    }
}
