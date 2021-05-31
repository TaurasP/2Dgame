public class Potion extends Item{

    public Potion(String name, int price, int health) {
        this.type = "Potion";
        this.name = name;
        this.price = price;
        this.lifePoints = health;
        this.isEquipped = false;
    }
}
