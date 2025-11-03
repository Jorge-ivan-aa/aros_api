package accrox.aros.api.application.dto.table;

public class TableStatusOutput {
    private Long id;
    private String name;
    private String status;
    private Long currentOrderId;

    public TableStatusOutput() {
    }

    public TableStatusOutput(Long id, String name, String status, Long currentOrderId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.currentOrderId = currentOrderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(Long currentOrderId) {
        this.currentOrderId = currentOrderId;
    }
}
