package lim.seyeon.safe.stay.domain;

import java.util.List;

public interface AreaRepository {
    Area add(Area area);
    Area findAreaByAreaNum(Integer area_num);
    List<Area> findAllAreas();
    Double calculateAreaSafetyScore(Integer area_num);
    Area update(Area area);
    void delete(Integer area_num);
}
