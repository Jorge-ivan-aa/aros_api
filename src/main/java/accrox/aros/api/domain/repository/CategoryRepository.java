package accrox.aros.api.domain.repository;

import accrox.aros.api.domain.model.Category;

public interface CategoryRepository {
    /**
     * save a category
     * 
     * @param category category to save
     * 
     * @return info of the saved category
     */
    public Category create(Category category);

    /**
     * find a category using his name
     * 
     * @param name category's name
     * 
     * @return the finded category
     */
    public boolean existsByName(String name);
}