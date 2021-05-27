public class Potion extends Item{
    int health;
    boolean isUsed;

    public Potion(String name, int price, int health) {
        this.name = name;
        this.type = Game.POTION;
        this.price = price;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
