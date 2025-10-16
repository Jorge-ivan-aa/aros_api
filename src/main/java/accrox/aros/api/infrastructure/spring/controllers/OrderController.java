package accrox.aros.api.infrastructure.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accrox.aros.api.application.usecases.order.MarkOrderAsCompletedUseCase;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    @Autowired
    private MarkOrderAsCompletedUseCase markOrderAsCompletedUseCase;

    @PatchMapping(path = "/{id}/mark-order-as-completed")
    public ResponseEntity<Void> markAsCompleted(@PathVariable("id") Long id) {
        markOrderAsCompletedUseCase.execute(id);
        return ResponseEntity.accepted().build();
    }
}
