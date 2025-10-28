package accrox.aros.api.application.dto.table;

public class CreateTableDto {

    private String name;

    public CreateTableDto() {
    }

    public CreateTableDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
