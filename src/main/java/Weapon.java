public class Weapon extends Item{

    public Weapon(String name, int price, int damage) {
        this.type = "Weapon";
        this.name = name;
        this.price = price;
        this.damage = damage;
        this.isEquipped = false;
    }
}
