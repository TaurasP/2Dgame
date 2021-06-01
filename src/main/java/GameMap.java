import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private String name;
    private Location lastLocation;
    public List<Location> locations;

    public GameMap(String name) {
        this.name = name;
        locations = new ArrayList<>();
        lastLocation = new Location("");
    }

    public String getName() {
        return name;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }
}
