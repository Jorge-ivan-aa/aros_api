package accrox.aros.api.application.dto.daymenu;

import java.util.Collection;

public record DayMenuCategorySimple (
    Long category,
    Short position,
    Collection<Long> products
) {
    // 
}
