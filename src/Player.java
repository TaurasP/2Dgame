import java.util.ArrayList;
import java.util.List;

public class Player extends BaseCharacter {
    private String name;

    //public Game game;
    public Map map;
    public static List<Achievement> achievements;

    public Player() {
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 10;
        achievements = new ArrayList<>();
    }

    public Player(String name) {
        this.name = name;
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 10;
        achievements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Achievement> getAchievements() {
        return achievements;
    }
}
