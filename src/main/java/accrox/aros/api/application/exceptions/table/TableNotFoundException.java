package accrox.aros.api.application.exceptions.table;

public class TableNotFoundException extends Exception {
    public TableNotFoundException(String message) {
        super(message);
    }

    public TableNotFoundException() {
        this("The table could't be found");
    }
}
