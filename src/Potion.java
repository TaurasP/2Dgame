public class Potion extends Item{
    public Potion(String name, int price, int health) {
        this.name = name;
        this.type = Game.POTION;
        this.price = price;
        this.lifePoints = health;
        this.isEquipped = false;
    }
}
