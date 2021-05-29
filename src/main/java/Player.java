import java.util.ArrayList;
import java.util.List;

public class Player extends BaseCharacter {

    public Map map;
    public static List<Achievement> achievementsList = new ArrayList<>();
    public static List<Item> weaponsList = new ArrayList<>();
    public static List<Item> armorList = new ArrayList<>();
    public static List<Item> potionsList = new ArrayList<>();

    public static int enemiesKilledCounter;

    public static int baseDamagePoints = 5;

    public Player() {
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = baseDamagePoints;
        boolean isAlive = true;
        gold = 100; // pagal lygi padaryti skirtinga gold kieki?
        enemiesKilledCounter = 0;
    }

    public Player(String name) {
        this.name = name;
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 0;
        boolean isAlive = true;
        gold = 100; // pagal lygi padaryti skirtinga gold kieki?
        enemiesKilledCounter = 0;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
