package accrox.aros.api.application.exceptions.daymenu;

public class InvalidDayMenuDateException extends Exception {
    public InvalidDayMenuDateException(String message) {
        super(message);
    }

    public InvalidDayMenuDateException() {
        this("The day menu date must be today's date");
    }
}