package accrox.aros.api.infrastructure.spring.dto.orders;

import accrox.aros.api.application.dto.order.UpdateOrderInput;
import accrox.aros.api.domain.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request to update an existing order. All fields except 'id' are optional and only provided fields will be updated.", example = """
                {
                  "id": 1,
                  "status": "COMPLETED",
                  "table": 5,
                  "responsible": 2
                }
                """)
public record UpdateOrderRequest(
                @Schema(description = "ID of the order to update", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @Positive Long id,

                @Schema(description = "New status for the order (PENDING, COMPLETED, or CANCELLED)", example = "COMPLETED", requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Nullable OrderStatus status,

                @Schema(description = "New table ID for the order", example = "5", requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Nullable @Positive Long table,

                @Schema(description = "New responsible user ID for the order", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Nullable @Positive Long responsible) {
        public UpdateOrderInput toInput() {
                return new UpdateOrderInput(
                                this.id,
                                this.status,
                                this.table,
                                this.responsible);
        }
}
