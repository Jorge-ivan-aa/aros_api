package accrox.aros.api.infrastructure.spring.controllers;

import accrox.aros.api.application.dto.order.DetailOrderOutput;
import accrox.aros.api.application.dto.order.OrdersOutput;
import accrox.aros.api.application.exceptions.order.EmptyDayMenuSelectionException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.application.usecases.order.*;
import accrox.aros.api.infrastructure.spring.dto.orders.CreateOrderRequest;
import accrox.aros.api.infrastructure.spring.security.UserDetailsAdapter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(
        OrderController.class
    );

    @Autowired
    private GetOrdersByStatusUseCase getOrdersByStatusUseCase;

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase;
    
    @Autowired
    private GetOrdersByResponsibleUseCase getOrdersByResponsibleUseCase;

    @Autowired
    private GetAllDetailOrderUseCase getAllDetailOrderUseCase;

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
        @Valid @RequestBody CreateOrderRequest request,
        @AuthenticationPrincipal UserDetailsAdapter authentication
    )
        throws ProductNotFoundException, TableNotFoundException, EmptyDayMenuSelectionException {
        logger.info(
            "POST /api/orders/create - Creating new order for table {} with {} products",
            request.table(),
            request.clientOrders().size()
        );
        this.createOrderUseCase.execute(request.toInput(), authentication.getUser()
        );
        logger.info(
            "POST /api/orders/create - Order created successfully for table {}",
            request.table()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            tags = "Orders Management",
            summary = "Get all orders",
            description = "Retrieves a list of all available orders in the system.")
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrdersOutput>> getAllOrders() {
        logger.info("GET /api/orders/all - Retrieving all orders");
        List<OrdersOutput> orders = getOrdersByStatusUseCase.execute(null);
        logger.info("GET /api/orders/all - Retrieved {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }


    @Operation(
            tags = "Orders Management",
            summary = "Get all orders with details",
            description = "Retrieves all orders along with their client orders and products."
    )
    @GetMapping("/details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<DetailOrderOutput>> getAllDetailOrders() {
        logger.info("GET /api/orders/details - Retrieving all orders with details");

        List<DetailOrderOutput> orders = getAllDetailOrderUseCase.execute();

        if (orders.isEmpty()) {
            logger.info("GET /api/orders/details - No orders found");
            return ResponseEntity.noContent().build();
        }

        logger.info("GET /api/orders/details - Retrieved {} detailed orders", orders.size());
        return ResponseEntity.ok(orders);
    }


    @Operation(
            tags = "Orders Management",
            summary = "Get orders by status",
            description = "Retrieves all orders filtered by status (PENDING, COMPLETED, or CANCELLED). If the status is invalid or not provided, all orders will be returned."
    )
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrdersOutput>> getOrdersByStatus(@PathVariable String status) {
        logger.info("GET /api/orders/status/{} - Fetching orders by status", status);

        try {
            List<OrdersOutput> orders = getOrdersByStatusUseCase.execute(status);

            if (orders.isEmpty()) {
                logger.info("GET /api/orders/status/{} - No orders found", status);
                return ResponseEntity.noContent().build();
            }

            logger.info("GET /api/orders/status/{} - {} orders found", status, orders.size());
            return ResponseEntity.ok(orders);

        } catch (RuntimeException e) {
            logger.error("GET /api/orders/status/{} - Error: {}", status, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
    
    @Operation(
        tags = "Orders Management",
        summary = "Get orders by responsible/worker/employee",
        description = "Retrieves all orders filtered by responsible (id)"
    )
    @GetMapping("/responsible/{responsible}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrdersOutput>> getOrdersByResponsible(
        @PathVariable Long responsible
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            this.getOrdersByResponsibleUseCase.execute(responsible)
        );
    }
}
