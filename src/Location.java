import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;

    public static List<Enemy> enemies;

    public String getName() {
        return name;
    }

    public Location(String name) {
        this.name = name;
        enemies = new ArrayList<>();
    }
}
