package lim.seyeon.safe.stay.presentation.Filter;

public class PostFilter {
    private Long categoryId;
    private String sort;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getSort() {
        return sort;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
