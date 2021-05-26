import java.util.ArrayList;
import java.util.List;

public class Map {
    private String name;
    private Location lastLocation;
    public List<Location> locations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Map(String name) {
        this.name = name;
        locations = new ArrayList<>();
        lastLocation = new Location("");
    }
}
