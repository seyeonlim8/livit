package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.CrimeData;
import lim.seyeon.safe.stay.domain.CrimeDataRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
@Repository
public class ListCrimeDataRepository implements CrimeDataRepository {

    private List<CrimeData> crimeDataList = new CopyOnWriteArrayList<>();

    public CrimeData add(CrimeData crimeData) {
        crimeDataList.add(crimeData);
        return crimeData;
    }

    public List<CrimeData> findCrimeDataByAreaNum(Integer area_num) {
        return crimeDataList.stream()
                .filter(crimeData -> crimeData.getArea().equals(area_num))
                .toList();
    }

    public void deleteAllCrimes() {
        crimeDataList.clear();
    }

}
*/