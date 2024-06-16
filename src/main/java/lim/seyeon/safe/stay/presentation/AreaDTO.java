package lim.seyeon.safe.stay.presentation;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Area;

public class AreaDTO {

    @NotNull
    private Integer area_num;

    @NotNull
    private String name;

    @NotNull
    private Long population;

    private Integer safety_score;

    public AreaDTO() {}

    public AreaDTO(Integer area_num, String name, Long population) {
        this.area_num = area_num;
        this.name = name;
        this.population = population;
    }

    public AreaDTO(Integer area_num, String name, Long population, Integer safety_score) {
        this.area_num = area_num;
        this.name = name;
        this.population = population;
        this.safety_score = safety_score;
    }

    public Integer getArea_num() {
        return area_num;
    }

    public String getName() {
        return name;
    }

    public Long getPopulation() {
        return population;
    }

    public Integer getSafety_score() {
        return safety_score;
    }

    public void setArea_num(Integer area_num) {
        this.area_num = area_num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public void setSafety_score(Integer safety_score) {
        this.safety_score = safety_score;
    }

    public static Area toEntity (AreaDTO areaDTO) {
        Area area = new Area(
                areaDTO.getArea_num(),
                areaDTO.getName(),
                areaDTO.getPopulation(),
                areaDTO.getSafety_score()
        );
        return area;
    }

    public static AreaDTO toDTO (Area area) {
        AreaDTO areaDTO = new AreaDTO(
                area.getArea_num(),
                area.getName(),
                area.getPopulation(),
                area.getSafety_score()
        );
        return areaDTO;
    }
}
