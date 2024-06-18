package lim.seyeon.safe.stay.domain;

import java.awt.geom.Area;
import java.util.List;

public interface CrimeDataRepository {
    CrimeData add(CrimeData crimeData);
    List<CrimeData> findCrimeDataByAreaNum(Integer area_num);
    void deleteAllCrimes();
}
