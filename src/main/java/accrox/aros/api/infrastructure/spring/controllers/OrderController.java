package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.exceptions.order.EmptyDayMenuSelectionException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.application.usecases.order.CreateOrderUseCase;
import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;
import accrox.aros.api.infrastructure.spring.dto.orders.CreateOrderRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {
    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase;

    @PatchMapping(path = "/{id}/mark-order-as-completed")
    public ResponseEntity<Void> markAsCompleted(@PathVariable("id") Long id) {
        markOrderAsCompletedUseCase.execute(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateOrderRequest request
    ) throws ProductNotFoundException, TableNotFoundException, EmptyDayMenuSelectionException {
        this.createOrderUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
