package accrox.aros.api.infrastructure.spring.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import accrox.aros.api.application.usecases.order.CancelOverdueOrdersUseCase;
import accrox.aros.api.domain.service.LoggerService;
import accrox.aros.api.infrastructure.spring.adapters.LoggerAdapter;

@Configuration
public class LoggerBeansConfig {
    @Bean
    public LoggerService cancelOverdueOrdersLogger() {
        return new LoggerAdapter(CancelOverdueOrdersUseCase.class);
    }
}
