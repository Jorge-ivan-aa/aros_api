package accrox.aros.api.application.usecases.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import accrox.aros.api.application.dto.product.TopSellingProductOutput;
import accrox.aros.api.domain.model.Category;
import accrox.aros.api.domain.model.Product;
import accrox.aros.api.domain.repository.OrderRepository;
import accrox.aros.api.domain.repository.ProductRepository;

/**
 * Use case to obtain the top-N selling products from completed orders.
 * If there are fewer than N distinct products, returns only those found.
 */
public class GetTopSellingProductsUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public GetTopSellingProductsUseCase(
            OrderRepository orderRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * Returns the top "limit" selling products from completed orders.
     * 
     * @return list ordered by sold quantity desc; each item has product name, its
     *         category names and sold quantity
     */
    public List<TopSellingProductOutput> execute() {
        final int TOP_N = 10;

        // Get all sold product quantities from completed orders
        Map<Long, Long> soldQuantities = this.orderRepository.findSoldProductQuantitiesFromCompletedOrders();
        if (soldQuantities.isEmpty()) {
            return List.of();
        }

        Set<Long> ids = soldQuantities.keySet();

        // Load products with categories; then filter by ids.
        List<Product> allWithRelations = this.productRepository.findAllWithRelations();
        Map<Long, Product> byId = allWithRelations.stream()
                .filter(p -> ids.contains(p.getId()))
                .collect(Collectors.toMap(Product::getId, p -> p));

        // Sort by quantity desc and limit to TOP_N if necessary
        List<Map.Entry<Long, Long>> sorted = soldQuantities.entrySet()
                .stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .toList();

        List<Map.Entry<Long, Long>> top = sorted.size() > TOP_N ? sorted.subList(0, TOP_N) : sorted;

        List<TopSellingProductOutput> output = new ArrayList<>();
        for (Map.Entry<Long, Long> e : top) {
            Long productId = e.getKey();
            Long qty = e.getValue();

            Product p = byId.get(productId);
            if (p == null) {
                // Product may have been deleted; skip it to keep response consistent
                continue;
            }

            Collection<Category> categories = p.getCategories();
            List<String> categoryNames = categories == null
                    ? List.of()
                    : categories.stream()
                            .map(Category::getName)
                            .filter(n -> n != null && !n.isBlank())
                            .toList();

            output.add(new TopSellingProductOutput(
                    p.getName(),
                    categoryNames,
                    qty));
        }

        return output;
    }
}