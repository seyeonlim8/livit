package lim.seyeon.safe.stay.domain;

import java.util.List;
import java.util.Map;

public interface NeighborhoodRepository {
    Neighborhood add(Neighborhood neighborhood);
    Neighborhood findNeighborhoodByName(String name);
    List<Neighborhood> findAll();
    Neighborhood update(Neighborhood neighborhood);
    void delete(String name);
    Map<String, Double> calculateAndSetNeighborhoodSafetyScores();
}
