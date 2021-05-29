import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;

    public List<Enemy> enemies = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Location(String name) {
        this.name = name;
    }
}
