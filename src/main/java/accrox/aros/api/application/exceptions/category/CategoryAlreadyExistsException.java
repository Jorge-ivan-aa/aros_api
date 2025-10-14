package accrox.aros.api.application.exceptions.category;

public class CategoryAlreadyExistsException extends Exception {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException() {
        this("The category already exists");
    }
}
