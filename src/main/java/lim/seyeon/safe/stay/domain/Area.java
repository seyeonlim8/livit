package lim.seyeon.safe.stay.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "areas")
public class Area {

    @Id
    @Column(name = "area_num")
    private Integer area_num;

    @Column(name = "name")
    private String name;

    public Area() {}

    public Area(Integer area_num, String name) {
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
}
