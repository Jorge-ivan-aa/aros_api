package accrox.aros.api.application.usecases.table;

import accrox.aros.api.application.dto.table.TableStatusOutput;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.TableRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GetTablesStatusUseCase {

    private TableRepository repository;

    public GetTablesStatusUseCase(TableRepository repository) {
        this.repository = repository;
    }

    public List<TableStatusOutput> execute() {
        List<Table> tables = repository.findAllTablesWithOrders();
        LocalDate today = LocalDate.now();

        return tables
            .stream()
            .map(table -> {
                String status = "AVAILABLE";
                Long currentOrderId = null;

                // Buscar el pedido activo del día actual
                Order activeOrder = table
                    .getOrders()
                    .stream()
                    .filter(
                        order ->
                            order.getStatus() == OrderStatus.PENDING &&
                            order.getTakedAt() != null &&
                            order.getTakedAt().toLocalDate().equals(today)
                    )
                    .findFirst()
                    .orElse(null);

                if (activeOrder != null) {
                    status = "OCCUPIED";
                    currentOrderId = activeOrder.getId();
                }

                return new TableStatusOutput(
                    table.getId(),
                    table.getName(),
                    status,
                    currentOrderId
                );
            })
            .collect(Collectors.toList());
    }
}
