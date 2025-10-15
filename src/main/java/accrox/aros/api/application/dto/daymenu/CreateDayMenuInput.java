package accrox.aros.api.application.dto.daymenu;

import java.util.Collection;

public record CreateDayMenuInput (
    String name,
    Float price,
    Collection<DayMenuCategorySimple> products
) {
    // 
}
