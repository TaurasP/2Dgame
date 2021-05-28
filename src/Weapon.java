public class Weapon extends Item{
    public Weapon(String name, int price, int damage) {
        this.name = name;
        this.type = Game.WEAPON;
        this.price = price;
        this.damage = damage;
    }
}
