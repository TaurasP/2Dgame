public class Armor extends Item{
    public Armor(String name, int price, int armor) {
        this.name = name;
        this.type = Game.ARMOR;
        this.price = price;
        this.armor = armor;
        this.isEquipped = false;
    }
}
