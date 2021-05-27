public class Armor extends Item{
    int armor;

    public Armor(String name, int price, int armor) {
        this.name = name;
        this.type = Game.ARMOR;
        this.price = price;
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }
}
