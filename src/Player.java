import java.util.ArrayList;
import java.util.List;

public class Player extends BaseCharacter {


    public Map map;
    public static List<Achievement> achievementsList = new ArrayList<>();
    public static List<Item> weaponsList = new ArrayList<>();
    public static List<Item> armorList = new ArrayList<>();
    public static List<Item> potionsList = new ArrayList<>();
    Weapon knife = new Weapon("Knife", 100, 20);
    Weapon bow = new Weapon("Bow", 150, 20);
    Armor shield = new Armor("Shield", 100, 5);
    Armor helmet = new Armor("Helmet", 100, 5);
    Potion potion50 = new Potion("50 life points", 50, 50);
    Potion potion100 = new Potion("100 life points", 100, 100);

    public Player() {
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 20;
        boolean isAlive = true;
        weaponsList.add(knife);
        knife.setEquipped(true);
        weaponsList.add(bow);
        armorList.add(shield);
        shield.setEquipped(true);
        armorList.add(helmet);
        potionsList.add(potion50);
        potionsList.add(potion100);
        gold = 100; // pagal lygi padaryti skirtinga gold kieki
    }

    public Player(String name) {
        this.name = name;
        type = "Player";
        lifePoints = 100;
        armorPoints = 0;
        damagePoints = 0;
        boolean isAlive = true;
        weaponsList.add(knife);
        knife.setEquipped(true);
        weaponsList.add(bow);
        armorList.add(shield);
        shield.setEquipped(true);
        armorList.add(helmet);
        potionsList.add(potion50);
        potionsList.add(potion100);
        gold = 100; // pagal lygi padaryti skirtinga gold kieki
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


}
