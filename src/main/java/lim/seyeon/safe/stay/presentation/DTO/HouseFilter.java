package lim.seyeon.safe.stay.presentation.DTO;

public class HouseFilter {
    private String name;
    private String neighborhood;
    private Integer maxPrice;
    private Integer minPrice;

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
}
