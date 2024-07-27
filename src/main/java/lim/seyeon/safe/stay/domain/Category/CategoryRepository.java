package lim.seyeon.safe.stay.domain.Category;

import java.util.List;

public interface CategoryRepository {
    Category add(Category category);
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
    List<Category> findAllCategories();
    Category update(Category category);
    void delete(Long id);
}
