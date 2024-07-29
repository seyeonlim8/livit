package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Category.Category;

public class CategoryDTO {
    private Long id;
    @NotNull
    private String name;
    private String description;

    public CategoryDTO() {}

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category(
                categoryDTO.getId(),
                categoryDTO.getName(),
                categoryDTO.getDescription()
        );
        return category;
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
        return categoryDTO;
    }
}

