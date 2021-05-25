import java.util.ArrayList;
import java.util.List;

public class Map {
    private String name;
    public List<Location> locations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map(String name) {
        this.name = name;
        locations = new ArrayList<>();
    }
}
