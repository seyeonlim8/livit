package lim.seyeon.safe.stay.presentation.Controller.REST;

import lim.seyeon.safe.stay.application.CategoryService;
import lim.seyeon.safe.stay.presentation.DTO.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.add(categoryDTO);
    }

    @GetMapping(value = "/{id}")
    public CategoryDTO findCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @GetMapping(value = "/{name}")
    public CategoryDTO findCategoryById(@PathVariable String name) {
        return categoryService.findCategoryByName(name);
    }

    @GetMapping
    public List<CategoryDTO> findCategories() {
        return categoryService.findAllCategories();
    }

    @PutMapping(value = "/{id}")
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        return categoryService.update(categoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
