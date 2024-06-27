package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;

public class NeighborhoodDTO {

    private String name;

    @NotNull
    private Long population;

    @NotNull
    private Double safety_score;

    public NeighborhoodDTO() {}

    public NeighborhoodDTO(String name, Long population, Double safety_score) {
        this.name = name;
        this.population = population;
        this.safety_score = safety_score;
    }

    public String getName() {
        return name;
    }

    public Long getPopulation() {
        return population;
    }

    public Double getSafety_score() {
        return safety_score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public void setSafety_score(Double safety_score) {
        this.safety_score = safety_score;
    }

    public static Neighborhood toEntity(NeighborhoodDTO neighborhoodDTO) {
        Neighborhood neighborhood = new Neighborhood(
                neighborhoodDTO.getName(),
                neighborhoodDTO.getPopulation(),
                neighborhoodDTO.getSafety_score()
        );
        return neighborhood;
    }

    public static NeighborhoodDTO toDTO(Neighborhood neighborhood) {
        NeighborhoodDTO neighborhoodDTO = new NeighborhoodDTO(
                neighborhood.getName(),
                neighborhood.getPopulation(),
                neighborhood.getSafety_score()
        );
        return neighborhoodDTO;
    }

    public String toString() {
        return this.getName() + " (" + this.getPopulation() + ")";
    }
}
