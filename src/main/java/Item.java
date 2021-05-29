public class Item {
    String name;
    String type;
    int price;
    boolean isEquipped;
    int damage;
    int armor;
    int lifePoints;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public boolean isEquipped(boolean b) {
        return isEquipped;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }
}
