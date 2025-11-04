package accrox.aros.api.application.usecases.order;

import accrox.aros.api.application.dto.order.ClientDetailOrderOutput;
import accrox.aros.api.application.dto.order.DetailOrderOutput;
import accrox.aros.api.application.dto.order.ProductDetailOutput;
import accrox.aros.api.domain.model.ClientOrder;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.OrderRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GetAllDetailOrderUseCase {

    private final OrderRepository orderRepository;

    public GetAllDetailOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<DetailOrderOutput> execute() {
        List<Order> orders = orderRepository.findDetailAll();

        return orders.stream()
                .map(this::toDetailOrderOutput)
                .toList();
    }



    private DetailOrderOutput toDetailOrderOutput(Order order) {
        String status = order.getStatus() != null ? order.getStatus().name() : null;
        String takedAt = order.getTakedAt() != null
                ? order.getTakedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;

        List<ClientDetailOrderOutput> clientOrders = order.getClientOrders() == null
                ? Collections.emptyList()
                : order.getClientOrders().stream()
                .filter(Objects::nonNull)
                .map(this::toClientDetailOrderOutput)
                .toList();

        String tableName = order.getTable() != null ? order.getTable().getName() : null;
        String responsibleName = order.getResponsible() != null ? order.getResponsible().getName() : null;

        return new DetailOrderOutput(
                order.getId(),
                status,
                takedAt,
                order.getTotal(),
                tableName,
                responsibleName,
                clientOrders
        );
    }


    private ClientDetailOrderOutput toClientDetailOrderOutput(ClientOrder co) {
        String status = co.getStatus() != null ? co.getStatus().name() : null;

        List<ProductDetailOutput> details = co.getDetails() == null
                ? Collections.emptyList()
                : co.getDetails().stream()
                .filter(Objects::nonNull)
                .map(this::toProductDetailOutput)
                .toList();

        return new ClientDetailOrderOutput(
                co.getId(),
                co.getTotal(),
                status,
                details
        );
    }

    private ProductDetailOutput toProductDetailOutput(Product p) {
        return new ProductDetailOutput(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getQuantity(),
                p.getObservations()
        );
    }

}
