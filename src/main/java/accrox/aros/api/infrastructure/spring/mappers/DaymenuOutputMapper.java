package accrox.aros.api.infrastructure.spring.mappers;

import accrox.aros.api.application.dto.daymenu.DaymenuCategoryOutput;
import accrox.aros.api.application.dto.daymenu.DaymenuOutput;
import accrox.aros.api.application.dto.daymenu.DaymenuProductOutput;
import accrox.aros.api.domain.model.DayMenuCategory;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class DaymenuOutputMapper {

    public static DaymenuOutput toOutput(Daymenu daymenu) {
        if (daymenu == null) {
            return null;
        }

        List<DaymenuCategoryOutput> categories = daymenu.getSubProducts().stream()
                .map(DaymenuOutputMapper::mapDayMenuCategoryToOutput)
                .collect(Collectors.toList());

        return new DaymenuOutput(
                daymenu.getId(),
                daymenu.getName(),
                daymenu.getDescription(),
                daymenu.getPrice(),
                daymenu.getPreparationTime(),
                daymenu.getActive(),
                daymenu.getCreation(),
                categories
        );
    }

    private static DaymenuCategoryOutput mapDayMenuCategoryToOutput(DayMenuCategory dayMenuCategory) {
        if (dayMenuCategory == null) {
            return null;
        }

        List<DaymenuProductOutput> products = dayMenuCategory.getProducts().stream()
                .map(DaymenuOutputMapper::mapProductToOutput)
                .collect(Collectors.toList());

        return new DaymenuCategoryOutput(
                dayMenuCategory.getCategory().getId(),
                dayMenuCategory.getCategory().getName(),
                products,
                dayMenuCategory.getPosition()
        );
    }

    private static DaymenuProductOutput mapProductToOutput(Product product) {
        if (product == null) {
            return null;
        }

        return new DaymenuProductOutput(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getPreparationTime()
        );
    }
}
