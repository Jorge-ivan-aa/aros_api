package accrox.aros.api.application.dto.Table;

public class CreateTablesDto {

    private int count;

    public CreateTablesDto() {
    }

    public CreateTablesDto(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
