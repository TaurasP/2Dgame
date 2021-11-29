abstract class Item {
    String name;
    String type;
    int price;
    boolean isEquipped;
    int damage;
    int armor;
    int lifePoints;

    abstract String getName();

    abstract String getType();

    abstract int getPrice();

    abstract int getDamage();

    abstract int getArmor();

    abstract int getLifePoints();

    abstract void setIsEquipped(boolean equipped);

    abstract boolean getIsEquipped();
}
