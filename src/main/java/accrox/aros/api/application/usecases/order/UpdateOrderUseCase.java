package accrox.aros.api.application.usecases.order;

import accrox.aros.api.application.dto.order.UpdateOrderInput;
import accrox.aros.api.application.exceptions.order.OrderNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.TableRepository;
import accrox.aros.api.domain.repository.UserRepository;

public class UpdateOrderUseCase {
    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;
    private final UserRepository userRepository;

    public UpdateOrderUseCase(
            OrderRepository orderRepository,
            TableRepository tableRepository,
            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.userRepository = userRepository;
    }

    public void execute(UpdateOrderInput input) throws OrderNotFoundException, TableNotFoundException {
        Order existingOrder = this.orderRepository.findById(input.id())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + input.id()));

        // Validate table exists if it's being updated
        if (input.table() != null && !this.tableRepository.existsById(input.table())) {
            throw new TableNotFoundException();
        }

        // Validate user exists if responsible is being updated
        if (input.responsible() != null) {
            this.userRepository.findById(input.responsible())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + input.responsible()));
        }

        Order updatedOrder = this.mapInputToDomain(input, existingOrder);
        this.orderRepository.update(updatedOrder);
    }

    private Order mapInputToDomain(UpdateOrderInput input, Order existingOrder) {
        if (input.status() != null) {
            existingOrder.setStatus(input.status());
        }

        if (input.table() != null) {
            existingOrder.setTable(new Table(input.table(), null, null));
        }

        if (input.responsible() != null) {
            User user = new User();
            user.setId(input.responsible());
            existingOrder.setResponsible(user);
        }

        return existingOrder;
    }
}
