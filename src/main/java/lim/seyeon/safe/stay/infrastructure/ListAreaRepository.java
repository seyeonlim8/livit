package lim.seyeon.safe.stay.infrastructure;

/*
@Repository
@Profile("test")
public class ListAreaRepository implements AreaRepository {

    private List<Area> areas = new CopyOnWriteArrayList<>();

    @Override
    public Area add(Area area) {
        areas.add(area);
        return area;
    }

    @Override
    public Area findAreaByAreaNum(Integer area_num) {
        return areas.stream()
                .filter(area -> area.getArea_num().equals(area_num))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Area with area num " + area_num + " does not exist"));
    }

    @Override
    public List<Area> findAllAreas() {
        return areas;
    }

    @Override
    public Area update(Area area) {
        Integer index = areas.indexOf(area);
        areas.set(index, area);
        return area;
    }

    @Override
    public void delete(Integer area_num) {
        Area areaToDelete = this.findAreaByAreaNum(area_num);
        areas.remove(areaToDelete);
    }
}
*/