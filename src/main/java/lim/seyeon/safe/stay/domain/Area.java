package lim.seyeon.safe.stay.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "area")
public class Area {

    @Id
    @Column(name = "area_num")
    private Integer area_num;

    @Column(name = "name")
    private String name;

    @Column(name = "population")
    private Long population;

    @Column(name = "safety_score")
    private Integer safety_score;

    public Area() {}

    public Area(Integer area_num, String name, Long population) {
        this.area_num = area_num;
        this.name = name;
        this.population = population;
    }

    public Area(Integer area_num, String name, Long population, Integer safety_score) {
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
}
