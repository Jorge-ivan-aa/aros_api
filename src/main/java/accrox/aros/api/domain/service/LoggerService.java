package accrox.aros.api.domain.service;

public interface LoggerService {

    void info(String message);

    void warn(String message);

    void error(String message, Throwable throwable);
}
