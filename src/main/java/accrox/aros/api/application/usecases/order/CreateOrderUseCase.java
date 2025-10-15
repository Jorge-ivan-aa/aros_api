package accrox.aros.api.application.usecases.order;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import accrox.aros.api.application.dto.order.CreateOrderDetailInput;
import accrox.aros.api.application.dto.order.CreateOrderInput;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.domain.model.ClientOrder;
import accrox.aros.api.domain.model.DayMenuSelection;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.ProductRepository;

public class CreateOrderUseCase {
    private final OrderRepository repository;

    private final ProductRepository productRepository;

    private Map<Long, Product> products;

    public CreateOrderUseCase(
        OrderRepository repository,
        ProductRepository productRepository
    ) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public void execute(CreateOrderInput input) throws ProductNotFoundException {
        Collection<Long> subProductsIds = input.clientOrders().stream()
            .flatMap(co -> co.details().stream())
            .flatMap(cod -> cod.subProducts().stream())
            .collect(Collectors.toSet());

        Set<Long> productsIds = input.clientOrders().stream()
            .flatMap(co -> co.details().stream())
            .map(cod -> cod.product())
            .collect(Collectors.toSet());

        productsIds.addAll(subProductsIds);

        Collection<Product> products = this.productRepository.findAllByIdSimple(productsIds);

        if (products.size() != productsIds.size()) {
            throw new ProductNotFoundException();
        }

        products.forEach(p -> this.products.put(p.getId(), p));

        this.repository.create(this.transformInputIntoOrder(input));
    }

    private Order transformInputIntoOrder(CreateOrderInput input) {
        Order order = new Order();
        order.setTable(new Table(input.table(), null, null));
        order.setStatus(OrderStatus.PENDING);
        order.setTakedAt(LocalDateTime.now());
        order.setClientOrders(new LinkedList<>());

        input.clientOrders().stream().forEach((coi) -> {
            ClientOrder coe = new ClientOrder();
            coe.setOrder(order);
            coe.setStatus(OrderStatus.PENDING);
            coe.setDetails(coi.details().stream().map(this::detailToDomain).toList());
        });

        return order;
    }

    private Product detailToDomain(CreateOrderDetailInput input) {
        Product de = null;
        Product reference = this.products.get(input.product());

        if (input.subProducts() != null) {
            Collection<Product> selection = this.selectionToProduct(input.subProducts());
            DayMenuSelection des = new DayMenuSelection();
            des.setSelection(selection);
            de = des;
        } else {
            de = new Product();
        }

        de.setId(input.product());
        de.setName(reference.getName());
        de.setPrice(reference.getPrice());
        de.setQuantity(input.quantity());
        de.setObservations(input.observations());

        return de;
    }

    private Collection<Product> selectionToProduct(Collection<Long> selection) {
        return selection.stream().map(s -> {
            Product reference = this.products.get(s);
            Product product = new Product();

            product.setId(s);
            product.setName(reference.getName());

            return product;
        }).toList();
    }
}
