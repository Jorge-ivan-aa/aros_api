package accrox.aros.api.application.exceptions.order;

public class EmptyDayMenuSelectionException extends Exception {
    public EmptyDayMenuSelectionException(String message) {
        super(message);
    }

    public EmptyDayMenuSelectionException() {
        this("the daymenu's selection must no be empty");
    }
}
