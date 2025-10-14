package accrox.aros.api.application.exceptions.auth;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException() {
        this("the given credentials are invalid");
    }
}