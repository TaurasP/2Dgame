import java.util.ArrayList;
import java.util.List;

public class Shop {
    public List<Item> weaponsListInShop = new ArrayList<>();
    public List<Item> armorListInShop = new ArrayList<>();
    public List<Item> potionsListInShop = new ArrayList<>();

    public final String POTION_25_LP = "25 life points";
    public final String POTION_50_LP = "50 life points";
    public final String POTION_75_LP = "75 life points";

    public Weapon dagger = new Weapon("Dagger", 50, 20);
    public Weapon knife = new Weapon("Knife", 100, 25);
    public Weapon axe = new Weapon("Axe", 200, 45);
    public Weapon hammer = new Weapon("Hammer", 300, 95);
    public Weapon bow = new Weapon("Bow", 100, 25);
    public Weapon longBow = new Weapon("Long Bow", 150, 30);

    public Armor woodenShield = new Armor("Wooden Shield", 50, 5);
    public Armor ironShield = new Armor("Iron Shield", 100, 10);
    public Armor bigShield = new Armor("Big Shield", 200, 20);

    public Potion potion25 = new Potion(POTION_25_LP, 25, 25);
    public Potion potion50 = new Potion(POTION_50_LP, 50, 50);
    public Potion potion75 = new Potion(POTION_75_LP, 75, 75);

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

    public Potion getPotion25() {
        return potion25;
    }

    public Potion getPotion50() {
        return potion50;
    }

    public Potion getPotion75() {
        return potion75;
    }
}
