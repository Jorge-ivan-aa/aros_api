package accrox.aros.api.application.exceptions.product;

public class ProductAlreadyExistsException extends Exception {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException() {
        this("The product already exists");
    }
}
