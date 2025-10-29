package accrox.aros.api.infrastructure.spring.dto;

import accrox.aros.api.application.dto.table.UpdateTablesCountDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Request to update the total number of tables (create or delete to match total)", example = "{ \"total\": 4 }")
public record UpdateTablesCountRequest(
        @Schema(description = "Desired total number of tables", example = "4") @Min(value = 0, message = "total must be >= 0") int total) {
    public UpdateTablesCountDto toInput() {
        return new UpdateTablesCountDto(this.total);
    }
}
