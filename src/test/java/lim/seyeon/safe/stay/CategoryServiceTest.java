package lim.seyeon.safe.stay;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("Retrieves the newly added category when id is queried")
    void addAndFindCategoryByIdTest() {
        CategoryDTO categoryDTO = new CategoryDTO("category1", "description1");

        CategoryDTO addedCategory = categoryService.add(categoryDTO);
        Long id = addedCategory.getId();
        CategoryDTO foundCategory = categoryService.findCategoryById(id);

        assertEquals(addedCategory.getId(), foundCategory.getId());
        assertEquals(addedCategory.getName(), foundCategory.getName());
        assertEquals(addedCategory.getDescription(), foundCategory.getDescription());
    }

    @Test
    @DisplayName("Retrieves the newly added category when category name is queried")
    void addAndFindCategoryByNameTest() {
        CategoryDTO categoryDTO = new CategoryDTO("category1", "description1");

        CategoryDTO addedCategory = categoryService.add(categoryDTO);
        CategoryDTO foundCategory = categoryService.findCategoryByName(categoryDTO.getName());

        assertEquals(addedCategory.getId(), foundCategory.getId());
        assertEquals(addedCategory.getName(), foundCategory.getName());
        assertEquals(addedCategory.getDescription(), foundCategory.getDescription());
    }

    @Test
    @DisplayName("Throws EntityNotFoundException when a non-existent category id is queried")
    void throwsEntityNotFoundExceptionTest() {
        Long notFoundId = -1L;
        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(notFoundId));
    }

    @Test
    @DisplayName("All categories should be retrieved")
    void findAllCategoriesTest() {
        CategoryDTO categoryDTO1 = new CategoryDTO("category1", "description1");
        CategoryDTO categoryDTO2 = new CategoryDTO("category2", "description2");

        categoryService.add(categoryDTO1);
        categoryService.add(categoryDTO2);
        List<CategoryDTO> categories = categoryService.findAllCategories();

        assertEquals(2, categories.size());
    }

    @Test
    @DisplayName("Category should be successfully updated")
    void updateCategoryTest() {
        CategoryDTO categoryDTO = new CategoryDTO("category1", "description1");
        CategoryDTO addedCategory = categoryService.add(categoryDTO);

        addedCategory.setName("updatedName");
        CategoryDTO updatedCategory = categoryService.update(addedCategory);

        assertEquals("updatedName", updatedCategory.getName());
    }

    @Test
    @DisplayName("Category should be successfully deleted")
    void deleteCategoryTest() {
        CategoryDTO categoryDTO = new CategoryDTO("category1", "description1");
        categoryService.add(categoryDTO);

        categoryService.delete(categoryDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(categoryDTO.getId()));
    }
}
