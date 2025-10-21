package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.exceptions.order.EmptyDayMenuSelectionException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.application.usecases.order.CreateOrderUseCase;
import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;
import accrox.aros.api.infrastructure.spring.dto.orders.CreateOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(
        OrderController.class
    );

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase;

    @Operation(
        tags = "Orders Management",
        summary = "Mark an order as completed",
        description = "This endpoint updates the status of an order by marking it as completed. You must provide the order ID in the path parameter."
    )
    @PatchMapping(path = "/{id}/mark-order-as-completed")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> markAsCompleted(@PathVariable("id") Long id) {
        logger.info(
            "PATCH /api/orders/{}/mark-order-as-completed - Marking order as completed",
            id
        );
        markOrderAsCompletedUseCase.execute(id);
        logger.info(
            "PATCH /api/orders/{}/mark-order-as-completed - Order marked as completed successfully",
            id
        );
        return ResponseEntity.accepted().build();
    }

    @Operation(
        tags = "Orders Management",
        summary = "Create a new order",
        description = "This endpoint allows you to create a new order. You must provide the order details in the request body."
    )
    @PostMapping(path = "/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateOrderRequest request
    )
        throws ProductNotFoundException, TableNotFoundException, EmptyDayMenuSelectionException {
        logger.info(
            "POST /api/orders/create - Creating new order for table {} with {} products",
            request.table(),
            request.clientOrders().size()
        );
        this.createOrderUseCase.execute(request.toInput());
        logger.info(
            "POST /api/orders/create - Order created successfully for table {}",
            request.table()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
