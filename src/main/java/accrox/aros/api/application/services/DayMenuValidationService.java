package accrox.aros.api.application.services;

import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.Daymenu;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DayMenuValidationService {

    /**
     * Validates day menu selections when ordering a day menu product
     *
     * @param daymenu The day menu being ordered
     * @param selectedProductIds The customer's selected product IDs
     * @throws IllegalArgumentException if validation fails
     */
    public void validateDayMenuSelections(
        Daymenu daymenu,
        List<Long> selectedProductIds
    ) {
        if (daymenu == null) {
            throw new IllegalArgumentException("Day menu cannot be null");
        }

        if (selectedProductIds == null || selectedProductIds.isEmpty()) {
            throw new IllegalArgumentException(
                "At least one selection is required for day menu"
            );
        }

        // Get all available categories from the day menu
        Set<Long> availableCategoryIds = daymenu
            .getSubProducts()
            .stream()
            .map(dmc -> dmc.getCategory().getId())
            .collect(Collectors.toSet());

        // Get selected category IDs by finding which categories the selected products belong to
        Set<Long> selectedCategoryIds = selectedProductIds
            .stream()
            .map(productId -> findCategoryForProduct(daymenu, productId))
            .collect(Collectors.toSet());

        // Validate that all required categories are selected
        if (!selectedCategoryIds.containsAll(availableCategoryIds)) {
            throw new IllegalArgumentException(
                "Missing selections for required categories. Required: " +
                    availableCategoryIds +
                    ", Selected categories: " +
                    selectedCategoryIds
            );
        }

        // Validate that exactly one product is selected per category
        validateOneProductPerCategory(daymenu, selectedProductIds);

        // Validate each selection is valid
        for (Long productId : selectedProductIds) {
            validateProductInDayMenu(daymenu, productId);
        }
    }

    /**
     * Validates that exactly one product is selected per category
     */
    private void validateOneProductPerCategory(
        Daymenu daymenu,
        List<Long> selectedProductIds
    ) {
        for (DayMenuCategory menuCategory : daymenu.getSubProducts()) {
            long productsInThisCategory = selectedProductIds
                .stream()
                .filter(productId ->
                    isProductInCategory(menuCategory, productId)
                )
                .count();

            if (productsInThisCategory == 0) {
                throw new IllegalArgumentException(
                    "No product selected for category: " +
                        menuCategory.getCategory().getName()
                );
            }

            if (productsInThisCategory > 1) {
                throw new IllegalArgumentException(
                    "Multiple products selected for category: " +
                        menuCategory.getCategory().getName() +
                        ". Only one product per category is allowed."
                );
            }
        }
    }

    /**
     * Validates that a product is available in the day menu
     */
    private void validateProductInDayMenu(Daymenu daymenu, Long productId) {
        boolean isValidProduct = daymenu
            .getSubProducts()
            .stream()
            .anyMatch(menuCategory ->
                isProductInCategory(menuCategory, productId)
            );

        if (!isValidProduct) {
            throw new IllegalArgumentException(
                "Product " + productId + " is not available in this day menu"
            );
        }
    }

    /**
     * Finds the category ID for a given product in the day menu
     */
    private Long findCategoryForProduct(Daymenu daymenu, Long productId) {
        return daymenu
            .getSubProducts()
            .stream()
            .filter(menuCategory ->
                isProductInCategory(menuCategory, productId)
            )
            .map(menuCategory -> menuCategory.getCategory().getId())
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Product " +
                        productId +
                        " is not available in this day menu"
                )
            );
    }

    /**
     * Checks if a product is available in a specific menu category
     */
    private boolean isProductInCategory(
        DayMenuCategory menuCategory,
        Long productId
    ) {
        return menuCategory
            .getProducts()
            .stream()
            .anyMatch(product -> product.getId().equals(productId));
    }

    /**
     * Gets the available product IDs for a specific category in a day menu
     */
    public List<Long> getAvailableProductsForCategory(
        Daymenu daymenu,
        Long categoryId
    ) {
        return daymenu
            .getSubProducts()
            .stream()
            .filter(dmc -> dmc.getCategory().getId().equals(categoryId))
            .findFirst()
            .map(dmc ->
                dmc
                    .getProducts()
                    .stream()
                    .map(product -> product.getId())
                    .collect(Collectors.toList())
            )
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Category " + categoryId + " not found in day menu"
                )
            );
    }

    /**
     * Gets all available category IDs in a day menu
     */
    public Set<Long> getAvailableCategoryIds(Daymenu daymenu) {
        return daymenu
            .getSubProducts()
            .stream()
            .map(dmc -> dmc.getCategory().getId())
            .collect(Collectors.toSet());
    }
}
