package lim.seyeon.safe.stay.domain.Neighborhood;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
