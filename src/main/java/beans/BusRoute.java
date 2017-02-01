package beans;

/**
 * Created by mvkent on 1/18/2017.
 */
public class BusRoute implements DbObject {
    private long id;
    private Bus bus;
    private Station station;
    private int sort;
    private int duration;
    private boolean selected = false;

    public BusRoute(long id, long busId, long stationId, int sort, int duration) {
        this.id = id;
        this.bus = new Bus().fetchObject(busId);
        this.station = new Station().fetchObject(stationId);
        this.sort = sort;
        this.duration = duration;
    }

    public int getSort() {
        return sort;
    }

    @Override
    public long getId() {
        return id;
    }

    public Bus getBus() {
        return bus;
    }

    public Station getStation() {
        return station;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public DbObject fetchObject(long id) {
        return this;
    }

    @Override
    public boolean pushObject() {
        return false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
