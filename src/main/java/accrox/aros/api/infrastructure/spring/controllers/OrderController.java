package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.usecases.order.CreateOrderUseCase;
import accrox.aros.api.infrastructure.spring.dto.orders.CreateOrderRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {
    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateOrderRequest request
    ) throws ProductNotFoundException {
        this.createOrderUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
