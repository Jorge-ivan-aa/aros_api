package accrox.aros.api.application.exceptions.category;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
        this("category doesn't exists");
    }
}
