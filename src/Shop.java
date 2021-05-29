import java.util.ArrayList;
import java.util.List;

public class Shop {
    public static List<Item> weaponsListInShop = new ArrayList<>();
    public static List<Item> armorListInShop = new ArrayList<>();
    public static List<Item> potionsListInShop = new ArrayList<>();

    public static final String POTION_25_LP = "25 life points";
    public static final String POTION_50_LP = "50 life points";
    public static final String POTION_75_LP = "75 life points";

    public static Weapon dagger = new Weapon("Dagger", 50, 20);
    public static Weapon knife = new Weapon("Knife", 100, 25);
    public static Weapon axe = new Weapon("Axe", 200, 45);
    public static Weapon hammer = new Weapon("Hammer", 300, 95);
    public static Weapon bow = new Weapon("Bow", 100, 25);
    public static Weapon longBow = new Weapon("Long Bow", 150, 30);

    public static Armor woodenShield = new Armor("Wooden Shield", 50, 5);
    public static Armor ironShield = new Armor("Iron Shield", 100, 10);
    public static Armor bigShield = new Armor("Big Shield", 200, 20);

    public static Potion potion25 = new Potion(POTION_25_LP, 25, 25);
    public static Potion potion50 = new Potion(POTION_50_LP, 50, 50);
    public static Potion potion75 = new Potion(POTION_75_LP, 75, 75);

    public Shop() {
        int numberOfPotions = 100;

        weaponsListInShop.add(dagger);
        weaponsListInShop.add(knife);
        weaponsListInShop.add(axe);
        weaponsListInShop.add(hammer);
        weaponsListInShop.add(bow);
        weaponsListInShop.add(longBow);

        armorListInShop.add(woodenShield);
        armorListInShop.add(ironShield);
        armorListInShop.add(bigShield);

        addPotionsToShop(numberOfPotions);
    }

    public void addPotionsToShop(int numberOfPotions) {
        for (int i = 0; i < numberOfPotions; i++) {
            potionsListInShop.add(potion25);
            potionsListInShop.add(potion50);
            potionsListInShop.add(potion75);
        }
    }
}
