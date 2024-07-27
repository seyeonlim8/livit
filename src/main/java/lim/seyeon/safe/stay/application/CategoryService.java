package lim.seyeon.safe.stay.application;

import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Category.CategoryRepository;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    public CategoryRepository categoryRepository;
    public ValidationService validationService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ValidationService validationService) {
        this.categoryRepository = categoryRepository;
        this.validationService = validationService;
    }

    public CategoryDTO add(CategoryDTO categoryDTO) {
        Category category = CategoryDTO.toEntity(categoryDTO);
        validationService.checkValid(category);

        Category savedCategory = categoryRepository.add(category);
        CategoryDTO savedCategoryDTO = CategoryDTO.toDTO(savedCategory);
        return savedCategoryDTO;
    }

    public CategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findCategoryById(id);
        CategoryDTO categoryDTO = CategoryDTO.toDTO(category);
        return categoryDTO;
    }

    public CategoryDTO findCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByName(name);
        CategoryDTO categoryDTO = CategoryDTO.toDTO(category);
        return categoryDTO;
    }

    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = categoryRepository.findAllCategories();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> CategoryDTO.toDTO(category))
                .toList();
        return categoryDTOS;
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = CategoryDTO.toEntity(categoryDTO);
        Category updatedCategory = categoryRepository.update(category);
        CategoryDTO updatedCategoryDTO = CategoryDTO.toDTO(updatedCategory);
        return updatedCategoryDTO;
    }

    public void delete(Long id) {
        categoryRepository.delete(id);
    }
}
