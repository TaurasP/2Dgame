import java.util.ArrayList;
import java.util.List;

public class Map {
    private String name;
    private Location location1 = new Location("Vilnius");
    private Location location2 = new Location("Klaipeda");

    public static List<Location> locations = new ArrayList<>();

    public static List<Location> getLocations() {
        return locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map(String name) {
        this.name = name;
        Map.locations.add(location1);
        Map.locations.add(location2);
    }
}
