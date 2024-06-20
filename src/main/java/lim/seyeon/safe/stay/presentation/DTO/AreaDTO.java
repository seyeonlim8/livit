package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.Area.Area;

public class AreaDTO {

    @NotNull
    private Integer area_num;

    @NotNull
    private String name;

    public AreaDTO() {}

    public AreaDTO(Integer area_num, String name) {
        this.area_num = area_num;
        this.name = name;
    }

    public Integer getArea_num() {
        return area_num;
    }

    public String getName() {
        return name;
    }

    public void setArea_num(Integer area_num) {
        this.area_num = area_num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Area toEntity (AreaDTO areaDTO) {
        Area area = new Area(
                areaDTO.getArea_num(),
                areaDTO.getName()
        );
        return area;
    }

    public static AreaDTO toDTO (Area area) {
        AreaDTO areaDTO = new AreaDTO(
                area.getArea_num(),
                area.getName()
        );
        return areaDTO;
    }
}
