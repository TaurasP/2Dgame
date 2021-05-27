public class Weapon extends Item{
    int damage;

    public Weapon(String name, int price, int damage) {
        this.name = name;
        this.type = Game.WEAPON;
        this.price = price;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
