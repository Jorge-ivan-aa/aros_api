package accrox.aros.api.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
     * check for category using his id
     * 
     * @param ids ids to search
     * 
     * @return the category exists
     */
    public boolean existsAllById(Collection<Long> ids);

    /**
     * find a category using his name
     * 
     * @param name category's name
     * 
     * @return the finded category
     */
    public boolean existsByName(String name);

    public boolean existsById(Long id);

    public Optional<Category> findByName(String name);
    
    public List<Category> findAll(); 

    public void deleteById(Long id);
}