package accrox.aros.api.application.exceptions.daymenu;

public class DayMenuAlreadyExistsException extends Exception {
    public DayMenuAlreadyExistsException(String message) {
        super(message);
    }

    public DayMenuAlreadyExistsException() {
        this("A day menu already exists for today");
    }
}