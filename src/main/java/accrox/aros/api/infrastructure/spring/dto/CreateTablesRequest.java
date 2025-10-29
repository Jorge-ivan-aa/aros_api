package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.table.CreateTablesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Request to create multiple tables at once", example = "{ \"count\": 5 }")
public record CreateTablesRequest(
        @Schema(description = "Number of tables to create", example = "3") @Min(value = 1, message = "count must be at least 1") int count) {
    public CreateTablesDto toInput() {
        return new CreateTablesDto(this.count);
    }
}
