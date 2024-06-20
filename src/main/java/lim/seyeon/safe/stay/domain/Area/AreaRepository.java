package lim.seyeon.safe.stay.domain.Area;

import java.util.List;

public interface AreaRepository {
    Area add(Area area);
    Area findAreaByAreaNum(Integer area_num);
    List<Area> findAllAreas();
    Area update(Area area);
    void delete(Integer area_num);
}
