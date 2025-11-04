package accrox.aros.api.application.exceptions.order;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        this("The order could't be found");
    }
}
