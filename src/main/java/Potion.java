public class Potion extends Item{

    public Potion(String name, int price, int health) {
        this.type = "Potion";
        this.name = name;
        this.price = price;
        this.lifePoints = health;
        this.isEquipped = false;
    }

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
