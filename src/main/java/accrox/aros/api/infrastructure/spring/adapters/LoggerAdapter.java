package accrox.aros.api.infrastructure.spring.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accrox.aros.api.domain.service.LoggerService;

public class LoggerAdapter implements LoggerService {

    private final Logger slf4jLogger;

    public LoggerAdapter(Class<?> loggingClass) {
        this.slf4jLogger = LoggerFactory.getLogger(loggingClass);
    }

    @Override
    public void info(String message) {
        slf4jLogger.info(message);
    }

    @Override
    public void warn(String message) {
        slf4jLogger.warn(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        slf4jLogger.error(message, throwable);
    }
}