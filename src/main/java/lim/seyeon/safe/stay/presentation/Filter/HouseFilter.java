package lim.seyeon.safe.stay.presentation.Filter;

public class HouseFilter {
    private String name;
    private String neighborhood;
    private Integer maxPrice;
    private Integer minPrice;
    private String sort;

    public String getName() {
        return name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public String getSort() {
        return sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
