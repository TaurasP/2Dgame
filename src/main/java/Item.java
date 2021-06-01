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

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setIsEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }
}
