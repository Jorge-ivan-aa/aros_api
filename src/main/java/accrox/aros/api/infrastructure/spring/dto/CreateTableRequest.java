package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.table.CreateTableDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to create a single table. The application assigns the numeric name automatically if not provided.", example = "{ \"name\": \"1\" }")
public record CreateTableRequest(
        @Schema(description = "Optional name for the table. If omitted or null, the system will assign the next available number.") @Size(max = 50) String name) {
    public CreateTableDto toInput() {
        return new CreateTableDto(this.name);
    }
}
