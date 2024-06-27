package lim.seyeon.safe.stay.domain.Neighborhood;

import jakarta.persistence.*;
import lim.seyeon.safe.stay.domain.House.House;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "neighborhoods")
public class Neighborhood {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "population")
    private Long population;

    @Column(name = "safety_score")
    private Double safety_score;

    @OneToMany(mappedBy = "neighborhood", cascade = CascadeType.ALL)
    private Set<House> houses;

    public Neighborhood() {}

    public Neighborhood(String name, Long population, Double safety_score) {
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
}
