import java.util.ArrayList;
import java.util.List;

public class Player extends BaseCharacter {
    private String name;
    int gold;

    public Map map;
    public static List<Achievement> achievementsList = new ArrayList<>();
    public static List<Item> weaponsList = new ArrayList<>();
    public static List<Item> armorList = new ArrayList<>();
    public static List<Item> potionsList = new ArrayList<>();

    public Player() {
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 20;
        boolean isAlive;
        weaponsList.add(new Weapon("Knife", 100, 20));
        weaponsList.add(new Weapon("Arrow", 150, 25));
        armorList.add(new Armor("Shield", 100, 5));
        armorList.add(new Armor("Helmet", 100, 5));
        potionsList.add(new Potion("100 life points", 100, 100 ));
        potionsList.add(new Potion("50 life points", 50, 50));
    }

    public Player(String name) {
        this.name = name;
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 20;
        boolean isAlive;
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
}
