package accrox.aros.api.application.usecases.order;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import accrox.aros.api.application.dto.order.CreateClientOrderInput;
import accrox.aros.api.application.dto.order.CreateOrderDetailInput;
import accrox.aros.api.application.dto.order.CreateOrderInput;
import accrox.aros.api.application.exceptions.order.EmptyDayMenuSelectionException;
import accrox.aros.api.application.exceptions.product.ProductNotFoundException;
import accrox.aros.api.application.exceptions.table.TableNotFoundException;
import accrox.aros.api.domain.model.ClientOrder;
import accrox.aros.api.domain.model.DayMenuSelection;
import accrox.aros.api.domain.model.Order;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.model.Table;
import accrox.aros.api.domain.model.User;
import accrox.aros.api.domain.model.enums.OrderStatus;
import accrox.aros.api.domain.repository.DaymenuRepository;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.ProductRepository;
import accrox.aros.api.domain.repository.TableRepository;

public class CreateOrderUseCase {
    private final OrderRepository repository;

    private final ProductRepository productRepository;

    private final DaymenuRepository daymenuRepository;

    private final TableRepository tableRepository;

    private Map<Long, Product> products;

    public CreateOrderUseCase(
        OrderRepository repository,
        ProductRepository productRepository,
        DaymenuRepository daymenuRepository,
        TableRepository tableRepository
    ) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.tableRepository = tableRepository;
        this.daymenuRepository = daymenuRepository;
        this.products = new HashMap<>();
    }

    public void execute(
        CreateOrderInput input,
        User responsible
    ) throws
        ProductNotFoundException,
        TableNotFoundException,
        EmptyDayMenuSelectionException
    {
        if (! this.tableRepository.existsById(input.table())) {
            throw new TableNotFoundException();
        }

        Collection<Product> products = this.validateProductsInputAndRetrieve(input);

        products.forEach(p -> this.products.put(p.getId(), p));
        Order order = this.transformInputIntoOrder(input);
        order.setResponsible(responsible);

        this.repository.create(order);
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
            coe.setTotal((float) coe.getDetails().stream().mapToDouble(Product::calculateTotal).sum());

            order.addClientOrder(coe);
        });

        order.setTotal((float) order.getClientOrders().stream().mapToDouble(ClientOrder::getTotal).sum());

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

    private Collection<Product> validateProductsInputAndRetrieve(
        CreateOrderInput input
    ) throws ProductNotFoundException, EmptyDayMenuSelectionException {
        Collection<Long> subProductsIds = input.clientOrders().stream()
            .flatMap(co -> co.details().stream())
            .flatMap(cod -> cod.subProducts() != null ? cod.subProducts().stream() : Stream.empty())
            .collect(Collectors.toSet());

        Set<Long> productsIds = input.clientOrders().stream()
            .flatMap(co -> co.details().stream())
            .map(cod -> cod.product())
            .collect(Collectors.toSet());

        Set<Long> allProductsIds = new LinkedHashSet<>();
        allProductsIds.addAll(productsIds);
        allProductsIds.addAll(subProductsIds);

        Collection<Product> products = this.productRepository.findAllByIdSimple(allProductsIds);

        if (products.size() != allProductsIds.size()) {
            throw new ProductNotFoundException();
        }

        Collection<Long> daymenus = this.daymenuRepository.findIdsIn(productsIds);
        this.validateDayMenuHasSubProducts(input, daymenus);

        return products;
    }

    private void validateDayMenuHasSubProducts(
        CreateOrderInput input,
        Collection<Long> daymenuIds
    ) throws EmptyDayMenuSelectionException {
        if (daymenuIds.isEmpty()) {
            return;
        }

        for (CreateClientOrderInput co : input.clientOrders()) {
            for (CreateOrderDetailInput detail : co.details()) {
                if (
                    daymenuIds.contains(detail.product())
                    && detail.subProducts() == null
                ) {
                    throw new EmptyDayMenuSelectionException();
                }
            }
        }
    }
}
